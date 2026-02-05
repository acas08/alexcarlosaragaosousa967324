
package com.seplag.acervo.controller;

import com.seplag.acervo.domain.Album;
import com.seplag.acervo.dto.AlbumCompletoDto;
import com.seplag.acervo.dto.AlbumDto;
import com.seplag.acervo.dto.AlbumImagemDto;
import com.seplag.acervo.enumeradores.ModalidadeEnum;
import com.seplag.acervo.service.AlbumImagemService;
import com.seplag.acervo.service.AlbumService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/albuns")
@Tag(name = "Albuns", description = "Endpoints para gerenciamento de albuns do acervo")
public class AlbumControllerV1 {

    @Autowired
    private AlbumService albumService;

    @Autowired
    private AlbumImagemService albumImagemService;

    @Value("${minio.expiry-seconds:1800}")
    private int expirySeconds;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Listar albuns",
            description = "Retorna a lista de todos os albuns cadastrados no acervo."
    )
    public Page<AlbumCompletoDto> listar(@RequestParam(defaultValue = "0") @Min(0) int page,
                                         @RequestParam(defaultValue = "10") @Min(1) @Max(10) int size) {
        return albumService.findAll(PageRequest.of(page, size));
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

    @GetMapping(value = "/listarPorModalidade", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Listar álbuns por modalidade do artista (paginado)")
    public Page<AlbumCompletoDto> listarPorModalidade(
            @RequestParam ModalidadeEnum modalidade,
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "10") @Min(1) @Max(10) int size,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        Sort.Direction direction = Sort.Direction.fromString(sortDir);
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, "descricao"));
        return albumService.buscarPorModalidadeArtista(modalidade, pageable);
    }

    @PostMapping(
            value = "/{id}/imagens",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Anexar 1 ou mais imagens ao álbum (MinIO)")
    public ResponseEntity<List<AlbumImagemDto>> anexarImagens(
            @PathVariable("id") Long albumId,
            @RequestParam("files") List<MultipartFile> files
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(albumImagemService.armazenarImagens(albumId, files));
    }

    @GetMapping("solicitarLink/{id}")
    public ResponseEntity<Map<String, Object>> solicitarLink(@PathVariable("id") Long id) {
        String url = albumImagemService.gerarLinkPreAssinado(id);
        return ResponseEntity.ok(Map.of(
                "url", url,
                "expiresInSeconds", expirySeconds
        ));
    }


}
