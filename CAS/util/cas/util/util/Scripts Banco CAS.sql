CREATE schema espaco_fisico;
CREATE schema ensino;
CREATE schema comum;
CREATE schema acesso;


CREATE TABLE comum.turno
(
id_turno integer not null,
descricao character varying (255),
CONSTRAINT pk_TurnoID PRIMARY KEY (id_turno)
);


CREATE TABLE comum.pessoa
(
id_pessoa integer not null,
nome character varying (255),
data_nascimento date,
cpf character varying (15),
registro_geral character varying (20),
nome_mae character varying (255),
nome_pai character varying (255),
endereco character varying (255),
bairro character varying (255),
cep character varying (9),
CONSTRAINT pk_PessoaID PRIMARY KEY (id_pessoa)
);


CREATE TABLE ensino.disciplina
(
id_disciplina integer not null,
nome character varying (255),
sigla character varying (30),
CONSTRAINT pk_DisciplinaID PRIMARY KEY (id_disciplina)
);


CREATE TABLE ensino.docente
(
id_docente integer not null,
matricula character varying (30),
id_pessoa integer,
CONSTRAINT pk_DocenteID PRIMARY KEY (id_docente),
FOREIGN KEY (id_pessoa) REFERENCES comum.pessoa(id_pessoa)
);


CREATE TABLE ensino.estudante
(
id_estudante integer not null,
matricula character varying (30),
id_pessoa integer,
curso character varying (255),
CONSTRAINT pk_EstudanteID PRIMARY KEY (id_estudante),
FOREIGN KEY (id_pessoa) REFERENCES comum.pessoa(id_pessoa)
);


CREATE TABLE ensino.horario
(
id_horario integer not null,
id_turno integer,
hora_inicio timestamp without time zone,
hora_time  timestamp without time zone,
dia_semana integer,
curso character varying (255),
CONSTRAINT pk_HorarioID PRIMARY KEY (id_horario),
FOREIGN KEY (id_turno) REFERENCES comum.turno(id_turno)
);


CREATE TABLE ensino.turma
(
id_turma integer not null,
codigo integer,
CONSTRAINT pk_TurmaID PRIMARY KEY (id_turma),
);


CREATE TABLE acesso.usuario
(
id_usuario integer not null,
login character varying (255),
senha character varying (255),
tipo_usuario integer,
CONSTRAINT pk_UsuarioID PRIMARY KEY (id_usuario),
);


CREATE TABLE espaco_fisico.aplicativo
(
id_aplicativo integer not null,
nome character varying (255),
CONSTRAINT pk_AplicativoID PRIMARY KEY (id_aplicativo),
);


CREATE TABLE espaco_fisico.laboratorio
(
id_laboratorio integer not null,
nome character varying (255),
CONSTRAINT pk_LaboratorioID PRIMARY KEY (id_laboratorio)
);


CREATE TABLE espaco_fisico.projetor
(
id_projetor integer not null,
marca integer,
CONSTRAINT pk_ProjetorID PRIMARY KEY (id_projetor)
);


CREATE TABLE espaco_fisico.sala
(
id_sala integer not null,
numero integer,
CONSTRAINT pk_SalaID PRIMARY KEY (id_sala)
);


CREATE TABLE espaco_fisico.local_aula
(
id_local_aula integer not null,
id_laboratorio integer,
id_sala integer,
bloco character varying (255),
capacidade integer,
CONSTRAINT pk_LocalAulaID PRIMARY KEY (id_local_aula),
FOREIGN KEY (id_laboratorio) REFERENCES espaco_fisico.laboratorio(id_laboratorio),
FOREIGN KEY (id_sala) REFERENCES espaco_fisico.sala(id_sala)
);


CREATE TABLE espaco_fisico.reserva
(
id_reserva integer not null,
usa_projetor boolean,
ativo boolean,
data Date,
id_turma integer,
id_local_aula integer,
CONSTRAINT pk_ReservaAulaID PRIMARY KEY (id_reserva),
FOREIGN KEY (id_turma) REFERENCES ensino.turma(id_turma),
FOREIGN KEY (id_local_aula) REFERENCES espaco_fisico.local_aula(id_local_aula)
);