package com.seplag.acervo.dto;

import com.seplag.acervo.domain.Artista;
import com.seplag.acervo.enumeradores.ModalidadeEnum;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class ArtistaDto {

    private String nome;
    private ModalidadeEnum modalidade;

    public static Artista toEntity(ArtistaDto artistaDto) {
        var modelMapper = new ModelMapper();
        return modelMapper.map(artistaDto, Artista.class);
    }
}
