package com.seplag.acervo.dto;

import com.seplag.acervo.domain.Album;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class AlbumDto {

    private String descricao;

    public static Album toEntity(AlbumDto albumDto) {
        var modelMapper = new ModelMapper();
        return modelMapper.map(albumDto, Album.class);
    }
}
