package com.education.education.model;

//create table disciplinas (
//        id int not null primary key auto_increment,
//        nome varchar(100),
//        codigo varchar(20),
//        curso_id int not null,
//        professor_id int not null,
//foreign key (curso_id) references cursos(id),
//foreign key (professor_id) references professores(id)
//        );
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.List;


@Entity
@Table(name = "disciplinas")
public class Disciplina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 100)
    private String nome;

    @Column(length = 20)
    private String codigo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "curso_id", referencedColumnName = "id")
    private Curso curso;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "professor_id", referencedColumnName = "id")
    private Professor professor;

//    @OneToMany(mappedBy = "disciplina",cascade = CascadeType.ALL, orphanRemoval = true)
//    @JsonIgnoreProperties("disciplina")
//    private List<Nota> notas;
//
//    public List<Nota> getNotas() {
//        return notas;
//    }
//
//    public void setNotas(List<Nota> notas) {
//        this.notas = notas;
//    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }
}
