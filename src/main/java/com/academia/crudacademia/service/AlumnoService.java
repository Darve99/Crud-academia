package com.academia.crudacademia.service;

import com.academia.crudacademia.dto.AlumnoRequest;
import com.academia.crudacademia.dto.AlumnoResponse;
import com.academia.crudacademia.exception.BusinessException;
import com.academia.crudacademia.exception.ResourceNotFoundException;
import com.academia.crudacademia.model.Alumno;
import com.academia.crudacademia.repository.AlumnoRepository;
import com.academia.crudacademia.repository.NotaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AlumnoService {

    private final AlumnoRepository alumnoRepository;
    private final NotaRepository notaRepository;

    @Transactional
    public AlumnoResponse create(AlumnoRequest request) {
        String normalizedEmail = request.email().trim().toLowerCase();

        if (alumnoRepository.existsByEmail(normalizedEmail)) {
            throw new BusinessException("Ya existe un alumno con ese email");
        }

        Alumno alumno = Alumno.builder()
                .nombre(request.nombre().trim())
                .apellido(request.apellido().trim())
                .email(normalizedEmail)
                .fechaNacimiento(request.fechaNacimiento())
                .build();

        return toResponse(alumnoRepository.save(alumno));
    }

    public List<AlumnoResponse> findAll() {
        return alumnoRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public AlumnoResponse findById(Long id) {
        Alumno alumno = alumnoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Alumno no encontrado con id " + id));
        return toResponse(alumno);
    }

    @Transactional
    public AlumnoResponse update(Long id, AlumnoRequest request) {
        Alumno alumno = alumnoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Alumno no encontrado con id " + id));

        String normalizedEmail = request.email().trim().toLowerCase();

        if (alumnoRepository.existsByEmailAndIdNot(normalizedEmail, id)) {
            throw new BusinessException("Ya existe otro alumno con ese email");
        }

        alumno.setNombre(request.nombre().trim());
        alumno.setApellido(request.apellido().trim());
        alumno.setEmail(normalizedEmail);
        alumno.setFechaNacimiento(request.fechaNacimiento());

        return toResponse(alumnoRepository.save(alumno));
    }

    @Transactional
    public void delete(Long id) {
        if (!alumnoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Alumno no encontrado con id " + id);
        }
        notaRepository.deleteByAlumnoId(id);
        alumnoRepository.deleteById(id);
    }

    private AlumnoResponse toResponse(Alumno alumno) {
        return new AlumnoResponse(
                alumno.getId(),
                alumno.getNombre(),
                alumno.getApellido(),
                alumno.getEmail(),
                alumno.getFechaNacimiento()
        );
    }
}
