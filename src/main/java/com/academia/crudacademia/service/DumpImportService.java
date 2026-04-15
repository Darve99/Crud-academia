package com.academia.crudacademia.service;

import com.academia.crudacademia.dto.DumpImportResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.sql.DataSource;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class DumpImportService {

    private static final String DUMPS_FOLDER = "dumps/";

    private final DataSource dataSource;

    public DumpImportResponse importBundledDump(String fileName) {
        validateDumpFileName(fileName);

        Resource resource = new ClassPathResource(DUMPS_FOLDER + fileName);
        if (!resource.exists()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el dump solicitado: " + fileName);
        }

        executeSqlScript(resource);
        return new DumpImportResponse(fileName, "IMPORTED", "Dump importado correctamente", LocalDateTime.now());
    }

    public DumpImportResponse importUploadedDump(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Debes enviar un archivo .dump no vacio");
        }

        String originalName = Objects.requireNonNullElse(file.getOriginalFilename(), "").trim();
        validateDumpFileName(originalName);

        Resource resource;
        try {
            byte[] content = file.getBytes();
            resource = new ByteArrayResource(content) {
                @Override
                public String getFilename() {
                    return originalName;
                }
            };
        } catch (IOException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No se pudo leer el archivo .dump");
        }

        executeSqlScript(resource);
        return new DumpImportResponse(originalName, "IMPORTED", "Archivo .dump importado correctamente", LocalDateTime.now());
    }

    public List<String> listBundledDumps() {
        return List.of("sample-data.dump", "sample-data2.dump");
    }

    private void executeSqlScript(Resource resource) {
        try (Connection connection = dataSource.getConnection()) {
            ScriptUtils.executeSqlScript(connection, new EncodedResource(resource, StandardCharsets.UTF_8));
        } catch (SQLException ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "No fue posible abrir la conexion a la base de datos");
        }
    }

    private void validateDumpFileName(String fileName) {
        if (fileName == null || fileName.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El nombre del archivo es obligatorio");
        }

        String normalized = fileName.trim();
        if (!normalized.toLowerCase().endsWith(".dump")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El archivo debe tener extension .dump");
        }

        if (!normalized.matches("^[a-zA-Z0-9._-]+$")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nombre de dump invalido");
        }
    }
}
