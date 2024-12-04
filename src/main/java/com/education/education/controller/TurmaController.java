package com.education.education.controller;

import com.education.education.dto.NotaResponseDTO;
import com.education.education.dto.TurmaRequestDTO;
import com.education.education.model.Curso;
import com.education.education.model.Matricula;
import com.education.education.model.Nota;
import com.education.education.model.Turma;
import com.education.education.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/turmas")
public class TurmaController {

    @Autowired
    private TurmaRepository repository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private NotaRepository notaRepository;

    @Autowired
    MatriculaRepository matriculaRepository;

    @Autowired
    AlunoRepository alunoRepository;

    @GetMapping
    public ResponseEntity<List<Turma>> finAll() {
        List<Turma> turmas = this.repository.findAll();
        return ResponseEntity.ok(turmas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Turma> findById(@PathVariable Integer id) {
        Turma turma = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Turma não encontrada."));

        return ResponseEntity.ok(turma);
    }

    @PostMapping
    public ResponseEntity<Turma> save(@RequestBody TurmaRequestDTO dto) {
        Turma turma = new Turma();
        turma.setAno(dto.ano());
        turma.setSemestre(dto.semestre());

        Curso curso = this.cursoRepository.findById(dto.curso_id())
                .orElseThrow(() -> new IllegalArgumentException("Curso não encontrado."));

        turma.setCurso(curso);
        Turma savedTurma = this.repository.save(turma);
        return ResponseEntity.ok(savedTurma);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Turma> update(@PathVariable Integer id, @RequestBody TurmaRequestDTO dto) {
        Turma turma = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Turma não encontrada."));

        turma.setAno(dto.ano());
        turma.setSemestre(dto.semestre());

        Curso curso = cursoRepository.findById(dto.curso_id())
                .orElseThrow(() -> new IllegalArgumentException("Curso não encontrado."));

        turma.setCurso(curso);

        Turma savedTurma = this.repository.save(turma);

        return ResponseEntity.ok(savedTurma);
    }

    @GetMapping("/{id}/notas")
    public ResponseEntity<List<Nota>> getNotes(@PathVariable Integer id) {
        Turma turma = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Turma não encontrada."));

        List<Nota> notas = new ArrayList<>();
        for(Matricula matricula : turma.getMatriculas()) {
            notas.addAll(matricula.getNotas());
        }

        return ResponseEntity.ok(notas);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        Turma turma = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Turma não encontrada."));

        this.repository.delete(turma);
        return ResponseEntity.noContent().build();
    }
}
