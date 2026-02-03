package com.seplag.acervo.dto;

import com.seplag.acervo.domain.Artista;
import com.seplag.acervo.domain.Regional;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class RegionalDto {

    private Integer id;
    private Integer codigo;
    private String nome;
    private Boolean ativo;

    public static Regional toEntity(RegionalDto regionalDto) {
        var modelMapper = new ModelMapper();
        return modelMapper.map(regionalDto, Regional.class);
    }

    public static RegionalDto toDto(Regional regional) {
        var modelMapper = new ModelMapper();
        return modelMapper.map(regional, RegionalDto.class);
    }
}
