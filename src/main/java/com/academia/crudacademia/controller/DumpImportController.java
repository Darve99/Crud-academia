package com.academia.crudacademia.controller;

import com.academia.crudacademia.dto.DumpImportResponse;
import com.academia.crudacademia.service.DumpImportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/import")
@RequiredArgsConstructor
public class DumpImportController {

    private final DumpImportService dumpImportService;

    @GetMapping("/dumps")
    @Operation(summary = "Listar dumps de prueba disponibles")
    public List<String> listDumps() {
        return dumpImportService.listBundledDumps();
    }

    @PostMapping("/dumps/{fileName}")
    @Operation(summary = "Importar un dump de prueba incluido en la API")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Dump importado correctamente"),
            @ApiResponse(responseCode = "404", description = "Dump no encontrado")
    })
    public ResponseEntity<DumpImportResponse> importBundled(@PathVariable String fileName) {
        return ResponseEntity.status(HttpStatus.CREATED).body(dumpImportService.importBundledDump(fileName));
    }

    @PostMapping(value = "/dumps/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Importar un archivo .dump enviado desde cliente")
    @RequestBody(content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
            schema = @Schema(type = "object", implementation = MultipartImportSchema.class)))
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Archivo .dump importado correctamente"),
            @ApiResponse(responseCode = "400", description = "Archivo invalido")
    })
    public ResponseEntity<DumpImportResponse> importUploaded(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.status(HttpStatus.CREATED).body(dumpImportService.importUploadedDump(file));
    }

    @Schema(name = "MultipartImportSchema")
    private static class MultipartImportSchema {
        @Schema(type = "string", format = "binary")
        public String file;
    }
}
