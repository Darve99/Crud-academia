package com.academia.crudacademia.controller;

import com.academia.crudacademia.dto.AlumnoRequest;
import com.academia.crudacademia.dto.AlumnoResponse;
import com.academia.crudacademia.service.AlumnoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/alumnos")
@RequiredArgsConstructor
public class AlumnoController {

    private final AlumnoService alumnoService;

    @PostMapping
    @Operation(summary = "Crear un alumno")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Alumno creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada invalidos")
    })
    public ResponseEntity<AlumnoResponse> create(@Valid @RequestBody AlumnoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(alumnoService.create(request));
    }

    @GetMapping
    public List<AlumnoResponse> findAll() {
        return alumnoService.findAll();
    }

    @GetMapping("/{id}")
    public AlumnoResponse findById(@PathVariable Long id) {
        return alumnoService.findById(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un alumno")
    public AlumnoResponse update(@PathVariable Long id, @Valid @RequestBody AlumnoRequest request) {
        return alumnoService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un alumno")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Alumno eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Alumno no encontrado")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        alumnoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
