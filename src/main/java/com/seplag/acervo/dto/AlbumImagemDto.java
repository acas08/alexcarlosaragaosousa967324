package com.seplag.acervo.dto;

import com.seplag.acervo.domain.AlbumImagem;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

@Getter
@Setter
public class AlbumImagemDto {
    private Long id;
    private String chaveRegistro;
    private String nomeOrginal;
    private String contentType;
    private Long sizeBytes;
    private LocalDateTime dataCriacao;

    public static AlbumImagemDto toDto(AlbumImagem img) {
        var mm = new ModelMapper();
        return mm.map(img, AlbumImagemDto.class);
    }
}
