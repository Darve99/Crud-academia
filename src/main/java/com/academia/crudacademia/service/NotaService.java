package com.academia.crudacademia.service;

import com.academia.crudacademia.dto.NotaRequest;
import com.academia.crudacademia.dto.NotaResponse;
import com.academia.crudacademia.exception.ResourceNotFoundException;
import com.academia.crudacademia.model.Alumno;
import com.academia.crudacademia.model.Materia;
import com.academia.crudacademia.model.Nota;
import com.academia.crudacademia.repository.AlumnoRepository;
import com.academia.crudacademia.repository.MateriaRepository;
import com.academia.crudacademia.repository.NotaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NotaService {

    private final NotaRepository notaRepository;
    private final AlumnoRepository alumnoRepository;
    private final MateriaRepository materiaRepository;

    @Transactional
    public NotaResponse create(NotaRequest request) {
        Alumno alumno = alumnoRepository.findById(request.alumnoId())
                .orElseThrow(() -> new ResourceNotFoundException("Alumno no encontrado con id " + request.alumnoId()));

        Materia materia = materiaRepository.findById(request.materiaId())
                .orElseThrow(() -> new ResourceNotFoundException("Materia no encontrada con id " + request.materiaId()));

        Nota nota = Nota.builder()
                .valor(request.valor())
                .alumno(alumno)
                .materia(materia)
                .build();

        return toResponse(notaRepository.save(nota));
    }

    public List<NotaResponse> findByAlumno(Long alumnoId, Long materiaId) {
        if (!alumnoRepository.existsById(alumnoId)) {
            throw new ResourceNotFoundException("Alumno no encontrado con id " + alumnoId);
        }

        if (materiaId != null && !materiaRepository.existsById(materiaId)) {
            throw new ResourceNotFoundException("Materia no encontrada con id " + materiaId);
        }

        List<Nota> notas = materiaId == null
                ? notaRepository.findByAlumnoId(alumnoId)
                : notaRepository.findByAlumnoIdAndMateriaId(alumnoId, materiaId);

        return notas.stream().map(this::toResponse).toList();
    }

    private NotaResponse toResponse(Nota nota) {
        Alumno alumno = nota.getAlumno();
        Materia materia = nota.getMateria();

        return new NotaResponse(
                nota.getId(),
                nota.getValor(),
                nota.getFechaRegistro(),
                alumno.getId(),
                alumno.getNombre() + " " + alumno.getApellido(),
                materia.getId(),
                materia.getNombre(),
                materia.getCodigo()
        );
    }
}
