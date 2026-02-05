package com.seplag.acervo.controller;

import com.seplag.acervo.domain.Artista;
import com.seplag.acervo.dto.ArtistaCompletoDto;
import com.seplag.acervo.dto.ArtistaDto;
import com.seplag.acervo.service.ArtistaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/artistas")
@Tag(name = "Artistas", description = "Endpoints para gerenciamento de artistas do acervo")
public class ArtistaControllerV1 {

    @Autowired
    private ArtistaService artistaService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Listar artistas",
            description = "Retorna a lista paginada de todos os artistas cadastrados no acervo."
    )
    public Page<ArtistaCompletoDto> listar(@RequestParam(defaultValue = "0") @Min(0) int page,
                                           @RequestParam(defaultValue = "10") @Min(1) @Max(10) int size) {
        return artistaService.findAll(PageRequest.of(page, size));
    }

    @GetMapping(value = "/listar", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Consultas por nome do artista com ordenação alfabética (asc/desc)",
            description = "Retorna uma página de artistas filtrando por nome (contém) e ordenando por nome asc."
    )
    public Page<ArtistaCompletoDto> buscarPorNome(
            @RequestParam(defaultValue = "") String nome,
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "10") @Min(1) @Max(10) int size,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        Sort.Direction direction = Sort.Direction.fromString(sortDir);
        return artistaService.findByNomeContaining(nome, PageRequest.of(page, size, Sort.by(direction, "nome")));
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

    @PutMapping(value = "/{id}/nome", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Alterar artista", description = "Altera o nome do artista.")
    public ResponseEntity<ArtistaCompletoDto> alterarNome(@PathVariable Long id, @RequestBody @Valid ArtistaDto artistaDto) {
        Artista artistaAtualizado = artistaService.atualizar(id, artistaDto);
        return ResponseEntity.ok(ArtistaCompletoDto.toDto(artistaAtualizado));
    }
}