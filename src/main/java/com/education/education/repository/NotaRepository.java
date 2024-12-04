package com.education.education.repository;

import com.education.education.model.Disciplina;
import com.education.education.model.Nota;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotaRepository extends JpaRepository<Nota, Integer> {
    List<Nota> findByDisciplina(Disciplina disciplina);
}
