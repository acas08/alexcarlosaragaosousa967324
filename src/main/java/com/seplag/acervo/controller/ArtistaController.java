package com.seplag.acervo.controller;

import com.seplag.acervo.domain.Artista;
import com.seplag.acervo.dto.ArtistaCompletoDto;
import com.seplag.acervo.dto.ArtistaDto;
import com.seplag.acervo.service.ArtistaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/artistas")
@Tag(name = "Artistas", description = "Endpoints para gerenciamento de artistas do acervo")
public class ArtistaController {

    @Autowired
    private ArtistaService artistaService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Listar artistas",
            description = "Retorna a lista de todos os artistas cadastrados no acervo."
    )
    public List<Artista> listar() {
        return artistaService.findAll();
    }

    @PostMapping
    @Operation(
            summary = "Incluir artistas",
            description = "Incluir artista no acervo."
    )
    public ResponseEntity<ArtistaCompletoDto> incluir(@RequestBody ArtistaDto artistaDto) {
        Artista artista = artistaService.save(ArtistaDto.toEntity(artistaDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(ArtistaCompletoDto.toDto(artista));
    }
}