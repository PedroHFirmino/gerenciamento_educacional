package com.education.education.repository;

import com.education.education.model.Curso;
import com.education.education.model.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoRepository extends JpaRepository<Curso, Integer> {
}
