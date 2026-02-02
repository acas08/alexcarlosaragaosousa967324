package com.seplag.acervo.dto;

import com.seplag.acervo.domain.Artista;
import org.modelmapper.ModelMapper;

public class ArtistaCompletoDto extends ArtistaDto{

    private Long id;

    public static ArtistaCompletoDto toDto(Artista artista) {
        var modelMapper = new ModelMapper();
        return modelMapper.map(artista, ArtistaCompletoDto.class);
    }
}
