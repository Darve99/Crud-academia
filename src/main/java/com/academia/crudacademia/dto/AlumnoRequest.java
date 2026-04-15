package com.academia.crudacademia.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record AlumnoRequest(
        @NotBlank @Size(max = 120) String nombre,
        @NotBlank @Size(max = 120) String apellido,
        @NotBlank @Email @Size(max = 160) String email,
        @NotNull @Past LocalDate fechaNacimiento
) {
}
