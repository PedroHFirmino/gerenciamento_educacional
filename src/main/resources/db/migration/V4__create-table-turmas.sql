create table turmas (
    id int not null primary key auto_increment,
    ano int,
    semestre int,
    curso_id int,
    CONSTRAINT FK_CursoTurma FOREIGN KEY (curso_id)
    REFERENCES cursos(id)
);