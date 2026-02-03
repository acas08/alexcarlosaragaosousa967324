package com.seplag.acervo.service;

import com.seplag.acervo.domain.Album;
import com.seplag.acervo.domain.AlbumImagem;
import com.seplag.acervo.dto.AlbumImagemDto;
import com.seplag.acervo.repository.AlbumImagemRepository;
import com.seplag.acervo.repository.AlbumRepository;
import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class AlbumImagemService {

    private final MinioClient minioInternalClient;
    private final MinioClient minioPublicClientAssinador;
    private final AlbumRepository albumRepository;
    private final AlbumImagemRepository imagemRepository;
    private final String bucket;
    private final int expirySeconds;

    public AlbumImagemService(
            MinioClient minioInternalClient,
            MinioClient minioPublicClientAssinador,
            AlbumRepository albumRepository,
            AlbumImagemRepository imagemRepository,
            @Value("${minio.bucket}") String bucket,
            @Value("${minio.expiry-seconds:1800}") int expirySeconds
    ) {
        this.minioInternalClient = minioInternalClient;
        this.minioPublicClientAssinador = minioPublicClientAssinador;
        this.albumRepository = albumRepository;
        this.imagemRepository = imagemRepository;
        this.bucket = bucket;
        this.expirySeconds = expirySeconds;
    }

    @Transactional
    public List<AlbumImagemDto> armazenarImagens(Long albumId, List<MultipartFile> files) {
        Album album = albumRepository.findById(albumId)
                .orElseThrow(() -> new IllegalArgumentException("Álbum não encontrado: " + albumId));

        if (files == null || files.isEmpty()) {
            throw new IllegalArgumentException("Envie pelo menos 1 imagem.");
        }

        try {
            boolean exists = minioInternalClient.bucketExists(BucketExistsArgs.builder().bucket(bucket).build());
            if (!exists) {
                minioInternalClient.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
            }
        } catch (Exception e) {
            throw new RuntimeException("Não foi possível validar/criar o bucket no MinIO.", e);
        }

        List<AlbumImagemDto> listaAlbumImagemDto = new ArrayList<>();

        for (MultipartFile file : files) {

            String objectKey = "albuns_" + albumId + "_" + UUID.randomUUID();


            try {
                minioInternalClient.putObject(
                        PutObjectArgs.builder()
                                .bucket(bucket)
                                .object(objectKey)
                                .contentType(file.getContentType())
                                .stream(file.getInputStream(), file.getSize(), -1)
                                .build()
                );
            } catch (Exception e) {
                throw new RuntimeException("Falha ao tentar enviar imagem para MinIO.", e);
            }

            AlbumImagem albumImagem = new AlbumImagem();
            albumImagem.setAlbum(album);
            albumImagem.setChaveRegistro(objectKey);
            albumImagem.setNomeOrginal(file.getOriginalFilename());
            albumImagem.setContentType(file.getContentType());
            albumImagem.setSizeBytes(file.getSize());
            albumImagem.setDataCriacao(LocalDateTime.now());

            AlbumImagem albumImagemSalvo = imagemRepository.save(albumImagem);
            listaAlbumImagemDto.add(AlbumImagemDto.toDto(albumImagemSalvo));
        }

        return listaAlbumImagemDto  ;
    }

    public String gerarLinkPreAssinado(Long albumImagemId) {

        AlbumImagem albumImagem = imagemRepository.findById(albumImagemId)
                .orElseThrow(() -> new IllegalArgumentException("AlbumImagem não encontrado com o ID: " + albumImagemId));

        try {
            return minioPublicClientAssinador.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket(bucket)
                            .object(albumImagem.getChaveRegistro())
                            .expiry(expirySeconds)
                            .build()
            );
        } catch (Exception e) {
            throw new IllegalStateException("Falha ao gerar URL pré-assinada para download", e);
        }
    }

}
