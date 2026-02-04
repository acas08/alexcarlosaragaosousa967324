package com.seplag.acervo.service;

import com.seplag.acervo.domain.AlbumImagem;
import com.seplag.acervo.repository.AlbumImagemRepository;
import com.seplag.acervo.repository.AlbumRepository;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AlbumImagemServiceTest {

    private AlbumImagemService service;

    @Mock
    private MinioClient minioInternalClient;

    @Mock
    private MinioClient minioPublicClientAssinador;

    @Mock
    private AlbumRepository albumRepository;

    @Mock
    private AlbumImagemRepository imagemRepository;

    @BeforeEach
    void setUp() {
        service = new AlbumImagemService(
                minioInternalClient,
                minioPublicClientAssinador,
                albumRepository,
                imagemRepository,
                "bucket-test",
                1800
        );
    }

    @Test
    void gerarLinkPreAssinado() throws Exception {
        Long albumImagemId = 10L;

        AlbumImagem imagem = mock(AlbumImagem.class);
        when(imagem.getChaveRegistro()).thenReturn("uid");

        when(imagemRepository.findById(albumImagemId)).thenReturn(Optional.of(imagem));
        when(minioPublicClientAssinador.getPresignedObjectUrl(any(GetPresignedObjectUrlArgs.class)))
                .thenReturn("http://localhost:9000/urlPreAssinada");

        String url = service.gerarLinkPreAssinado(albumImagemId);

        assertThat(url).isEqualTo("http://localhost:9000/urlPreAssinada");

    }

    @Test
    void gerarLinkPreAssinado_semImagem() {
        when(imagemRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> service.gerarLinkPreAssinado(999L));
        verify(imagemRepository).findById(999L);
        verifyNoInteractions(minioPublicClientAssinador);
    }

    @Test
    void armazenarImagens_semAlbum() {
        when(albumRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> service.armazenarImagens(1L, List.of(mock(MultipartFile.class))));
        verify(albumRepository).findById(1L);
    }
}