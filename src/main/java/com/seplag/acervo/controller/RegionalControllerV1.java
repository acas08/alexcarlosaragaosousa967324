package com.seplag.acervo.controller;

import com.seplag.acervo.service.RegionalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/regionais")
@Tag(name = "Regionais", description = "Endpoints para sincronização de regionais")
public class RegionalControllerV1 {

    @Autowired
    private RegionalService regionalService;

    @PostMapping
    @Operation(
            summary = "Sincronizar regionais",
            description = "Endpoint responsável por buscar as regionais do serviço externo argus da polícia civil e sincronizar com os registros que já estão salvos no banco de regionais."
    )
    public ResponseEntity<List> sincronizar() {
        return ResponseEntity.status(HttpStatus.CREATED).body(regionalService.sincronizar());
    }
}