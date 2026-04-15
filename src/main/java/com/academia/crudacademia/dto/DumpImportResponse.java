package com.academia.crudacademia.dto;

import java.time.LocalDateTime;

public record DumpImportResponse(
        String source,
        String status,
        String detail,
        LocalDateTime importedAt
) {
}
