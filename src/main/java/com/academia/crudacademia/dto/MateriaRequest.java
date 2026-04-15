package com.academia.crudacademia.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record MateriaRequest(
        @NotBlank @Size(max = 120) String nombre,
        @NotBlank @Size(max = 30) String codigo,
        @NotNull @Min(1) @Max(20) Integer creditos
) {
}
