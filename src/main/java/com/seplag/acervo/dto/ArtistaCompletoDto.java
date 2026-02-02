package com.seplag.acervo.dto;

import com.seplag.acervo.domain.Artista;
import com.seplag.acervo.enumeradores.ModalidadeEnum;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class ArtistaCompletoDto extends ArtistaDto{

    private Long id;
    private ModalidadeEnum modalidade;

    public static ArtistaCompletoDto toDto(Artista artista) {
        var modelMapper = new ModelMapper();
        return modelMapper.map(artista, ArtistaCompletoDto.class);
    }
}
