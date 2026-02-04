package com.seplag.acervo.dto;

import com.seplag.acervo.domain.Artista;
import com.seplag.acervo.enumeradores.ModalidadeEnum;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArtistaCompletoDtoTest {

    @Test
    void toDto() {
        Artista artista = new Artista();
        artista.setId(7L);
        artista.setNome("John Lennon");
        artista.setModalidade(ModalidadeEnum.CANTOR);

        ArtistaCompletoDto dto = ArtistaCompletoDto.toDto(artista);

        assertNotNull(dto);
        assertEquals("John Lennon", dto.getNome());
    }
}