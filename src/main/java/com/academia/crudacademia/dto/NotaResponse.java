package com.academia.crudacademia.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record NotaResponse(
        Long id,
        BigDecimal valor,
        LocalDateTime fechaRegistro,
        Long alumnoId,
        String alumnoNombreCompleto,
        Long materiaId,
        String materiaNombre,
        String materiaCodigo
) {
}
