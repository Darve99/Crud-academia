package com.academia.crudacademia.controller;

import com.academia.crudacademia.dto.NotaRequest;
import com.academia.crudacademia.dto.NotaResponse;
import com.academia.crudacademia.service.NotaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/notas")
@RequiredArgsConstructor
public class NotaController {

    private final NotaService notaService;

    @PostMapping
    @Operation(summary = "Registrar una nota")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Nota registrada correctamente"),
            @ApiResponse(responseCode = "404", description = "Alumno o materia no encontrada")
    })
    public ResponseEntity<NotaResponse> create(@Valid @RequestBody NotaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(notaService.create(request));
    }

    @GetMapping("/alumno/{alumnoId}")
    @Operation(summary = "Listar notas por alumno")
    public List<NotaResponse> findByAlumno(
            @PathVariable Long alumnoId,
            @RequestParam(required = false) Long materiaId
    ) {
        return notaService.findByAlumno(alumnoId, materiaId);
    }
}
