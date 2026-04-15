package com.academia.crudacademia.controller;

import com.academia.crudacademia.dto.MateriaRequest;
import com.academia.crudacademia.dto.MateriaResponse;
import com.academia.crudacademia.service.MateriaService;
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
@RequestMapping("/api/materias")
@RequiredArgsConstructor
public class MateriaController {

    private final MateriaService materiaService;

    @PostMapping
    @Operation(summary = "Crear una materia")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Materia creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada invalidos")
    })
    public ResponseEntity<MateriaResponse> create(@Valid @RequestBody MateriaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(materiaService.create(request));
    }

    @GetMapping
    public List<MateriaResponse> findAll() {
        return materiaService.findAll();
    }

    @GetMapping("/{id}")
    public MateriaResponse findById(@PathVariable Long id) {
        return materiaService.findById(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una materia")
    public MateriaResponse update(@PathVariable Long id, @Valid @RequestBody MateriaRequest request) {
        return materiaService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una materia")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Materia eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "Materia no encontrada")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        materiaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
