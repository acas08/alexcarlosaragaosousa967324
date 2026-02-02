package com.seplag.acervo.controller;

import com.seplag.acervo.domain.Album;
import com.seplag.acervo.dto.AlbumCompletoDto;
import com.seplag.acervo.dto.AlbumDto;
import com.seplag.acervo.service.AlbumService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/albuns")
@Tag(name = "Albuns", description = "Endpoints para gerenciamento de albuns do acervo")
public class AlbumController {

    @Autowired
    private AlbumService albumService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Listar albuns",
            description = "Retorna a lista de todos os albuns cadastrados no acervo."
    )
    public List<Album> listar() {
        return albumService.findAll();
    }

    @PostMapping
    @Operation(
            summary = "Incluir albuns",
            description = "Incluir album no acervo."
    )
    public ResponseEntity<AlbumCompletoDto> incluir(@RequestBody AlbumDto albumDto) {
        Album album = albumService.save(AlbumDto.toEntity(albumDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(AlbumCompletoDto.toDto(album));
    }
}