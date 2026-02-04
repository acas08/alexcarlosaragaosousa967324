package com.seplag.acervo.dto;

import com.seplag.acervo.domain.Album;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AlbumCompletoDtoTest {

    @Test
    void toDto() {
        Album album = new Album();
        album.setId(10L);
        album.setDescricao("Please Please Me");

        AlbumCompletoDto dto = AlbumCompletoDto.toDto(album);

        assertNotNull(dto);
        assertEquals("Please Please Me", dto.getDescricao());
    }
}