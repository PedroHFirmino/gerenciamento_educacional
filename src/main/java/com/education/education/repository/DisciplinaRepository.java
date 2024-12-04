package com.education.education.repository;

import com.education.education.model.Disciplina;
import com.education.education.model.Nota;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DisciplinaRepository extends JpaRepository<Disciplina, Integer> {}
