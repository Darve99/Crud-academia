package com.academia.crudacademia.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record NotaRequest(
        @NotNull @DecimalMin(value = "0.0", inclusive = true) @DecimalMax(value = "5.0", inclusive = true) BigDecimal valor,
        @NotNull Long alumnoId,
        @NotNull Long materiaId
) {
}
