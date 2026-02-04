package com.seplag.acervo.service;

import com.seplag.acervo.domain.Album;
import com.seplag.acervo.dto.AlbumCompletoDto;
import com.seplag.acervo.enumeradores.ModalidadeEnum;
import com.seplag.acervo.repository.AlbumRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AlbumServiceTest {

    @InjectMocks
    private AlbumService service;

    @Mock
    private AlbumRepository repository;

    @Mock
    private SimpMessagingTemplate messagingTemplate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void findAll() {
        Pageable pageable = PageRequest.of(0, 10);

        Album a1 = mock(Album.class);
        Album a2 = mock(Album.class);

        when(repository.findAll(pageable))
                .thenReturn(new PageImpl<>(List.of(a1, a2), pageable, 2));

        Page<AlbumCompletoDto> page = service.findAll(pageable);

        assertThat(page.getTotalElements()).isEqualTo(2);
        verify(repository).findAll(pageable);

    }

    @Test
    void save() {
        Album album = mock(Album.class);

        Album albumSalvo = mock(Album.class);
        when(albumSalvo.getId()).thenReturn(1L);
        when(albumSalvo.getDescricao()).thenReturn("A viagem");

        when(repository.save(album)).thenReturn(albumSalvo);

        Album retorno = service.save(album);

        assertThat(retorno).isEqualTo(albumSalvo);

        verify(repository).save(album);
        verify(messagingTemplate).convertAndSend(eq("/topic/albuns"), any(AlbumCompletoDto.class));

    }

    @Test
    void buscarPorModalidadeArtista() {
        Pageable pageable = PageRequest.of(0, 10);
        ModalidadeEnum modalidade = ModalidadeEnum.CANTOR;

        Album album = mock(Album.class);
        when(repository.findByModalidadeArtista(modalidade, pageable))
                .thenReturn(new PageImpl<>(List.of(album), pageable, 1));

        Page<AlbumCompletoDto> page = service.buscarPorModalidadeArtista(modalidade, pageable);

        assertThat(page.getTotalElements()).isEqualTo(1);
        verify(repository).findByModalidadeArtista(modalidade, pageable);

    }
}