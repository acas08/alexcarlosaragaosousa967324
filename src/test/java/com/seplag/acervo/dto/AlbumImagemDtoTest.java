package com.seplag.acervo.dto;

import com.seplag.acervo.domain.AlbumImagem;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class AlbumImagemDtoTest {

    @Test
    void toDto() {
        LocalDateTime now = LocalDateTime.of(2026, 2, 4, 7, 0);

        AlbumImagem img = new AlbumImagem();
        img.setId(1L);
        img.setChaveRegistro("minha_chave");
        img.setNomeOrginal("minha_foto.jpg");
        img.setContentType("minha_imagem/jpeg");
        img.setSizeBytes(123L);
        img.setDataCriacao(now);

        AlbumImagemDto dto = AlbumImagemDto.toDto(img);

        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals("minha_chave", dto.getChaveRegistro());
        assertEquals("minha_foto.jpg", dto.getNomeOrginal());
        assertEquals("minha_imagem/jpeg", dto.getContentType());
        assertEquals(123L, dto.getSizeBytes());
        assertEquals(now, dto.getDataCriacao());
    }
}