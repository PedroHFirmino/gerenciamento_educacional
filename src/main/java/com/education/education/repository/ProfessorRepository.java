package com.education.education.repository;

import com.education.education.model.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessorRepository extends JpaRepository<Professor, Integer> {
}
