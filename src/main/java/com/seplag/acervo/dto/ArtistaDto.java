package com.seplag.acervo.dto;

import com.seplag.acervo.domain.Artista;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class ArtistaDto {

    private String descricao;

    public static Artista toEntity(ArtistaDto artistaDto) {
        var modelMapper = new ModelMapper();
        return modelMapper.map(artistaDto, Artista.class);
    }
}
