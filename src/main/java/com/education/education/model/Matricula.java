package com.education.education.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "matriculas")
public class Matricula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "aluno_id", referencedColumnName = "id")
    @JsonIgnoreProperties("matriculas")
    private Aluno aluno;

    @ManyToOne
    @JoinColumn(name = "turma_id", referencedColumnName = "id")
    @JsonIgnoreProperties("matriculas")
    private Turma turma;

    @OneToMany(mappedBy = "matricula",cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("matricula")
    private List<Nota> notas;

    public List<Nota> getNotas() {
        return notas;
    }

    public void setNotas(List<Nota> notas) {
        this.notas = notas;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Turma getTurma() {
        return turma;
    }

    public void setTurma(Turma turma) {
        this.turma = turma;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Matricula matricula = (Matricula) o;
        return Objects.equals(aluno, matricula.aluno) && Objects.equals(turma, matricula.turma);
    }

    @Override
    public int hashCode() {
        return Objects.hash(aluno, turma);
    }
}
