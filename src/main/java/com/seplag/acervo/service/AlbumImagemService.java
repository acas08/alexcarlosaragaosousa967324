package com.seplag.acervo.service;

import com.seplag.acervo.domain.Album;
import com.seplag.acervo.domain.AlbumImagem;
import com.seplag.acervo.dto.AlbumImagemDto;
import com.seplag.acervo.repository.AlbumImagemRepository;
import com.seplag.acervo.repository.AlbumRepository;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.*;
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

    private final MinioClient minioClient;
    private final AlbumRepository albumRepository;
    private final AlbumImagemRepository imagemRepository;
    private final String bucket;

    public AlbumImagemService(
            MinioClient minioClient,
            AlbumRepository albumRepository,
            AlbumImagemRepository imagemRepository,
            @Value("${minio.bucket}") String bucket
    ) {
        this.minioClient = minioClient;
        this.albumRepository = albumRepository;
        this.imagemRepository = imagemRepository;
        this.bucket = bucket;
    }

    @Transactional
    public List<AlbumImagemDto> armazenarImagens(Long albumId, List<MultipartFile> files) {
        Album album = albumRepository.findById(albumId)
                .orElseThrow(() -> new IllegalArgumentException("Álbum não encontrado: " + albumId));

        if (files == null || files.isEmpty()) {
            throw new IllegalArgumentException("Envie pelo menos 1 imagem.");
        }

        try {
            boolean exists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket).build());
            if (!exists) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
            }
        } catch (Exception e) {
            throw new RuntimeException("Não foi possível validar/criar o bucket no MinIO.", e);
        }

        List<AlbumImagemDto> listaAlbumImagemDto = new ArrayList<>();

        for (MultipartFile file : files) {

            String objectKey = "albuns/" + albumId + "/" + UUID.randomUUID() + "-" + file.getOriginalFilename();


            try {
                minioClient.putObject(
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

        return listaAlbumImagemDto;
    }

}
