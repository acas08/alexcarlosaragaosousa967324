package com.seplag.acervo.service;


import com.seplag.acervo.domain.Artista;
import com.seplag.acervo.dto.ArtistaCompletoDto;
import com.seplag.acervo.dto.ArtistaDto;
import com.seplag.acervo.enumeradores.ModalidadeEnum;
import com.seplag.acervo.repository.ArtistaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ArtistaServiceTest {

    @Mock
    private ArtistaRepository repository;

    @InjectMocks
    private ArtistaService service;

    @Test
    void findAll() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("nome").ascending());

        Artista a1 = new Artista(1L, "ohn Lennon", ModalidadeEnum.CANTOR, null);
        Artista a2 = new Artista(2L, "Beatles", ModalidadeEnum.BANDA, null);

        when(repository.findAll(pageable)).thenReturn(new PageImpl<>(List.of(a1, a2), pageable, 2));

        Page<ArtistaCompletoDto> page = service.findAll(pageable);
        assertThat(page.getTotalElements()).isEqualTo(2);

        verify(repository).findAll(pageable);
    }

    @Test
    void findByNomeContaining() {
        Pageable pageable = PageRequest.of(0, 5);
        String filtro = "John";

        Artista a1 = new Artista(1L, "John Lennon", ModalidadeEnum.CANTOR, null);
        when(repository.findByNomeContaining(filtro, pageable))
                .thenReturn(new PageImpl<>(List.of(a1), pageable, 1));

        Page<ArtistaCompletoDto> page = service.findByNomeContaining(filtro, pageable);

        verify(repository).findByNomeContaining(filtro, pageable);
        assertThat(page.getContent().get(0).getNome()).isEqualTo("John Lennon");
    }

    @Test
    void save() {
        Artista artista = new Artista(1L, "John Lennon", ModalidadeEnum.CANTOR, null);
        when(repository.save(artista)).thenReturn(artista);

        Artista artistaSalvo = service.save(artista);

        verify(repository).save(artista);
        assertThat(artistaSalvo.getNome()).isEqualTo("John Lennon");

    }

    @Test
    void atualizar() {
        Artista artista = new Artista(1L, "John Lennon", ModalidadeEnum.CANTOR, null);
        when(repository.findById(artista.getId())).thenReturn(Optional.of(artista));
        when(repository.save(artista)).thenReturn(artista);

        Artista artistaAtualizado = service.atualizar(artista.getId(), ArtistaCompletoDto.toDto(artista));

        assertThat(artistaAtualizado.getNome()).isEqualTo("John Lennon");
    }
}