package com.seplag.acervo.dto;

import com.seplag.acervo.domain.Album;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class AlbumCompletoDto extends AlbumDto{

    private Long id;

    public static AlbumCompletoDto toDto(Album album) {
        var modelMapper = new ModelMapper();
        return modelMapper.map(album, AlbumCompletoDto.class);
    }
}
