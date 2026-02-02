package com.seplag.acervo.dto;

import com.seplag.acervo.domain.Album;
import org.modelmapper.ModelMapper;

public class AlbumCompletoDto extends ArtistaDto{

    private Long id;

    public static AlbumCompletoDto toDto(Album album) {
        var modelMapper = new ModelMapper();
        return modelMapper.map(album, AlbumCompletoDto.class);
    }
}
