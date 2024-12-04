package com.education.education.controller;

import com.education.education.dto.AlunoRequestDTO;
import com.education.education.dto.BoletimResponseDTO;
import com.education.education.dto.DisciplinaResponseDTO;
import com.education.education.dto.NotaResponseDTO;
import com.education.education.model.Aluno;
import com.education.education.model.Matricula;
import com.education.education.model.Nota;
import com.education.education.model.Turma;
import com.education.education.repository.AlunoRepository;
import com.education.education.repository.MatriculaRepository;
import com.education.education.repository.TurmaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/alunos")
public class AlunoController {

    @Autowired
    private AlunoRepository repository;

    @Autowired
    private MatriculaRepository matriculaRepository;

    @Autowired
    private TurmaRepository turmaRepository;

    @GetMapping
    public List<Aluno> finAll() {
        return this.repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Aluno> findById(@PathVariable Integer id) {
        Aluno aluno = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado."));
        return ResponseEntity.ok(aluno);
    }

    @PostMapping
    public ResponseEntity<Aluno> save(@RequestBody AlunoRequestDTO dto) {
        Aluno aluno = new Aluno();
        aluno.setNome(dto.nome());
        aluno.setEmail(dto.email());
        aluno.setData_nascimento(dto.data_nascimento());
        aluno.setMatricula(dto.matricula());

        return ResponseEntity.ok(this.repository.save(aluno)) ;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Aluno> update(@PathVariable Integer id, @RequestBody AlunoRequestDTO dto) {
        Aluno aluno = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado."));

        aluno.setNome(dto.nome());
        aluno.setEmail(dto.email());
        aluno.setData_nascimento(dto.data_nascimento());
        aluno.setMatricula(dto.matricula());

        return ResponseEntity.ok(this.repository.save(aluno));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        Aluno aluno = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado."));

        this.repository.delete(aluno);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{aluno_id}/matricula")
    public ResponseEntity<Aluno> addMatricula(@PathVariable Integer aluno_id, @RequestBody Integer turma_id) {
        Aluno aluno = this.repository.findById(aluno_id)
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado."));
        Turma turma = this.turmaRepository.findById(turma_id)
                .orElseThrow(() -> new IllegalArgumentException("Turma não encontrada."));

        Matricula matricula = new Matricula();
        matricula.setAluno(aluno);
        matricula.setTurma(turma);

        boolean matriculaJaExiste = aluno.getMatriculas().stream()
                .anyMatch(m -> m.getTurma().getId().equals(turma_id));

        if (!matriculaJaExiste) {
            aluno.addMatricula(matricula);
        } else {
            throw new IllegalArgumentException("O aluno já está matriculado nesta turma.");
        }


        Aluno alunoNota = this.repository.save(aluno);
        return ResponseEntity.ok(alunoNota);

    }

    @GetMapping("/{aluno_id}/boletim")
    public ResponseEntity<BoletimResponseDTO> getNotas(@PathVariable Integer aluno_id) {
        Aluno aluno = this.repository.findById(aluno_id)
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado."));
        List<NotaResponseDTO> notas = new ArrayList<>();
        if (!aluno.getMatriculas().isEmpty()) {
            for (Matricula matricula : aluno.getMatriculas()) {
                for (Nota nota : matricula.getNotas()) {
                    notas.add(
                            new NotaResponseDTO(
                                    nota.getNota(),
                                    nota.getData_lancamento(),
                                    new DisciplinaResponseDTO(
                                        nota.getDisciplina().getNome(),
                                        nota.getDisciplina().getCodigo()
                                    )
                            )
                    );
                }
            }
        }
        return ResponseEntity.ok(new BoletimResponseDTO(notas));
    }


}
