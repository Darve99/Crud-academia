package com.academia.crudacademia.repository;

import com.academia.crudacademia.model.Nota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface NotaRepository extends JpaRepository<Nota, Long> {

    List<Nota> findByAlumnoId(Long alumnoId);

    List<Nota> findByAlumnoIdAndMateriaId(Long alumnoId, Long materiaId);

    @Transactional
    void deleteByAlumnoId(Long alumnoId);

    @Transactional
    void deleteByMateriaId(Long materiaId);
}
