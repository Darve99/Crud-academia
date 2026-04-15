package com.academia.crudacademia.service;

import com.academia.crudacademia.dto.MateriaRequest;
import com.academia.crudacademia.dto.MateriaResponse;
import com.academia.crudacademia.exception.BusinessException;
import com.academia.crudacademia.exception.ResourceNotFoundException;
import com.academia.crudacademia.model.Materia;
import com.academia.crudacademia.repository.MateriaRepository;
import com.academia.crudacademia.repository.NotaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MateriaService {

    private final MateriaRepository materiaRepository;
    private final NotaRepository notaRepository;

    @Transactional
    public MateriaResponse create(MateriaRequest request) {
        String normalizedCode = request.codigo().trim().toUpperCase();

        if (materiaRepository.existsByCodigo(normalizedCode)) {
            throw new BusinessException("Ya existe una materia con ese codigo");
        }

        Materia materia = Materia.builder()
                .nombre(request.nombre().trim())
            .codigo(normalizedCode)
                .creditos(request.creditos())
                .build();

        return toResponse(materiaRepository.save(materia));
    }

    public List<MateriaResponse> findAll() {
        return materiaRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public MateriaResponse findById(Long id) {
        Materia materia = materiaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Materia no encontrada con id " + id));
        return toResponse(materia);
    }

    @Transactional
    public MateriaResponse update(Long id, MateriaRequest request) {
        Materia materia = materiaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Materia no encontrada con id " + id));

        String normalizedCode = request.codigo().trim().toUpperCase();

        if (materiaRepository.existsByCodigoAndIdNot(normalizedCode, id)) {
            throw new BusinessException("Ya existe otra materia con ese codigo");
        }

        materia.setNombre(request.nombre().trim());
        materia.setCodigo(normalizedCode);
        materia.setCreditos(request.creditos());

        return toResponse(materiaRepository.save(materia));
    }

    @Transactional
    public void delete(Long id) {
        if (!materiaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Materia no encontrada con id " + id);
        }
        notaRepository.deleteByMateriaId(id);
        materiaRepository.deleteById(id);
    }

    private MateriaResponse toResponse(Materia materia) {
        return new MateriaResponse(
                materia.getId(),
                materia.getNombre(),
                materia.getCodigo(),
                materia.getCreditos()
        );
    }
}
