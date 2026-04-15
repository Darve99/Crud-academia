package com.academia.crudacademia.dto;

public record MateriaResponse(
        Long id,
        String nombre,
        String codigo,
        Integer creditos
) {
}
