package com.academia.crudacademia.dto;

import java.time.LocalDate;

public record AlumnoResponse(
        Long id,
        String nombre,
        String apellido,
        String email,
        LocalDate fechaNacimiento
) {
}
