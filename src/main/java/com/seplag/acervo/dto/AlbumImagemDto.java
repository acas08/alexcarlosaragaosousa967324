package com.seplag.acervo.dto;

import com.seplag.acervo.domain.AlbumImagem;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class AlbumImagemDto {
    private Long id;
    private String objectKey;
    private String originalName;
    private String contentType;
    private Long sizeBytes;

    public static AlbumImagemDto toDto(AlbumImagem img) {
        var mm = new ModelMapper();
        return mm.map(img, AlbumImagemDto.class);
    }
}
