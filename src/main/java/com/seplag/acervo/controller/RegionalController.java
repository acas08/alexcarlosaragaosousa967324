package com.seplag.acervo.controller;

import com.seplag.acervo.domain.Album;
import com.seplag.acervo.dto.AlbumCompletoDto;
import com.seplag.acervo.dto.AlbumDto;
import com.seplag.acervo.dto.RegionalDto;
import com.seplag.acervo.enumeradores.ModalidadeEnum;
import com.seplag.acervo.service.AlbumService;
import com.seplag.acervo.service.RegionalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/regionais")
@Tag(name = "Regionais", description = "Endpoints para sincronização de regionais")
public class RegionalController {

    @Autowired
    private RegionalService regionalService;

    @PostMapping
    @Operation(
            summary = "Sincronizar regionais",
            description = "Sincronizar regionais"
    )
    public ResponseEntity<List> sincronizar() {
        return ResponseEntity.status(HttpStatus.CREATED).body(regionalService.sincronizar());
    }
}