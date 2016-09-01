/*drop table TURMA_HORARIO;

drop table HORARIO cascade;

drop table CURSO cascade;

drop table DISCIPLINA cascade;

drop table DOCENTE cascade;

drop table TURMA cascade;

drop table CREDITOMINISTRADO cascade;

drop table SALA cascade;

drop table DISCCURR cascade;

drop table CURRICULO cascade;

drop table CALOUROS cascade;

drop table CALOUROS_CURSO cascade;

drop table PEDIDOS_COORDENADORES cascade;

drop table VAGAS_SOLICITADAS cascade;

drop table VAGAS_ATENDIDAS cascade;

drop table PERIODO cascade;

drop table PERIODO_DISCIPLINA cascade;
*/
create table Turma_Horario(
	ID SERIAL PRIMARY KEY,
	TURMA_FK NUMERIC(19,0) not null,
	HORARIO_FK NUMERIC(19,0) not null
);

create table Horario(
	ID NUMERIC(19,0) not null,
	DIA DATE not null,
	primary key (ID)
);


create table Curso (
	ID NUMERIC(19,0) not null,
	NOME VARCHAR(21) not null,
	TURNO VARCHAR(10) not null,
	CODIGO VARCHAR(21) not null,
	primary key (ID),
	unique (CODIGO)
);
	
create table Disciplina (
	ID NUMERIC(19,0) not null,
	CODIGO VARCHAR(21) not null,
	CREDITO int not null,
	NOME VARCHAR(128) not null,
	PERFIL VARCHAR(50) not null,
	primary key (ID),
	UNIQUE (CODIGO)
);

create table Docente (
	ID NUMERIC(19,0) not null,
	CODIGO VARCHAR(21) not null,
	NOME VARCHAR(50) not null,
	NOME_COMPLETO VARCHAR(100) not null,
	CREDITACAO_ESPERADA int not null,
	primary key (ID),
	UNIQUE (CODIGO)
);

create table Turma(
	ID NUMERIC(19,0) not null,
	CODIGO VARCHAR(21) not null,
	TURNO VARCHAR (10) not null,
	MAX_VAGAS INT not null,
	ANO INT not null,
	SEMESTRE INT not null,
	HORARIO_FIXO BOOLEAN not null,
	DISCIPLINA_FK NUMERIC(19,0) not null,
	SALA_FK NUMERIC(19,0) not null,
	primary key(ID),
	UNIQUE (DISCIPLINA_FK)
);

create table CreditoMinistrado(
	CREDITO FLOAT,
	DOCENTE_FK NUMERIC(19,0),
	TURMA_FK NUMERIC(19,0)
);

create table Sala(
	ID NUMERIC(19,0) not null,
	NUMERO VARCHAR(10) not null,
	LOCAL VARCHAR(21) not null,
	primary key(ID),
	UNIQUE (NUMERO)
);

create table DiscCurr(
	ID NUMERIC(19,0) not null,
	PERIODO INT not null,
	CARACTER VARCHAR(50) not null,
	DISCIPLINA_FK NUMERIC(19,0) not null,
	CURRICULO_FK NUMERIC(19,0) not null,
	primary key (ID),
	UNIQUE (DISCIPLINA_FK)
);

create table Curriculo(
	ID NUMERIC(19,0) not null,
	ANO_INICIO INT not null,
	ATIVO BOOLEAN not null,
	CURSO_FK NUMERIC(19,0) not null,
	primary key(ID)
);

create table Calouros(
	ID NUMERIC(19,0) not null,
	NUM_VAGAS INT not null,
	primary key(ID),
	UNIQUE(NUM_VAGAS)
);

create table Calouros_Curso(
	ID SERIAL PRIMARY KEY,
	CURSO_FK NUMERIC(19,0) not null,
	CALOUROS_FK NUMERIC(19,0) not null
);

create table vagas_solicitadas(
	ID NUMERIC(19,0) not null,
	TOTAL_VAGAS INT not null,
	VAGAS_PERIOTIZADOS_OFERT INT not null,
	VAGAS_PERIOTIZADOS_NAO_OFERT INT not null,
	VAGAS_DESPERIOTIZADOS_OFERT INT not null,
	VAGAS_DESPERIOTIZADOS_NAO_OFERT INT not null,
	DESCRICAO VARCHAR(21) not null,
	DISCIPLINA_FK NUMERIC(19,0) not null,
	PEDIDOS_COORDENADORES_FK NUMERIC(19,0) not null,
	primary key (ID)
);

create table vagas_atendidas(
	ID NUMERIC(19,0) not null,
	TOTAL_VAGAS INT not null,
	VAGAS_PERIOTIZADOS_OFERT INT not null,
	VAGAS_PERIOTIZADOS_NAO_OFERT INT not null,
	VAGAS_DESPERIOTIZADOS_OFERT INT not null,
	VAGAS_DESPERIOTIZADOS_NAO_OFERT INT not null,
	DESCRICAO VARCHAR(21) not null,
	DISCIPLINA_FK NUMERIC(19,0) not null,
	PEDIDOS_COORDENADORES_FK NUMERIC(19,0) not null,
	primary key (ID)
);


create table pedidos_coordenadores(
	ID NUMERIC(19,0) not null,
	SEMESTRE INT not null,
	ANO INT not null,
	DATA DATE not null,
	NOME_COORDENADOR VARCHAR(21) not null,
	CURSO_FK NUMERIC(19,0) not null,
	primary key(ID)
);

create table periodo(
	ID numeric(19,0) not null,
	NUMERO_PERIODO numeric(19, 0) not null,
	CURSO_FK numeric(19,0) not null,
	primary key(ID)
);

create table Periodo_Disciplina(
	ID SERIAL PRIMARY KEY,
	PERIODO_FK NUMERIC(19,0) not null,
	DISCIPLINA_FK NUMERIC(19,0) not null
);

alter table PERIODO_DISCIPLINA
	add constraint FKC_PERIODO
	foreign key (PERIODO_FK)
	references PERIODO;

alter table PERIODO_DISCIPLINA
	add constraint FKC_DISCIPLINA
	foreign key (DISCIPLINA_FK)
	references DISCIPLINA;


alter table TURMA_HORARIO
	add constraint FKC_TURMA
	foreign key (TURMA_FK)
	references TURMA;

alter table TURMA_HORARIO
	add constraint FKC_HORARIO
	foreign key (HORARIO_FK)
	references HORARIO;
	
alter table TURMA
       add constraint FKC_TURMA_DISCIPLINA
       foreign key (DISCIPLINA_FK) 
       references DISCIPLINA;

alter table TURMA 
       add constraint FKC_TURMA_SALA
       foreign key (SALA_FK) 
       references SALA;
	   
alter table CREDITOMINISTRADO 
       add constraint FKC_CREDITOMINASTRADO_DOCENTE
       foreign key (DOCENTE_FK) 
       references DOCENTE;
	   
alter table CREDITOMINISTRADO 
       add constraint FKC_CREDITOMINASTRADO_TURMA
       foreign key (TURMA_FK) 
       references TURMA;
	   
alter table DISCCURR 
       add constraint FKC_DISCCURR_DISCIPLINA
       foreign key (DISCIPLINA_FK) 
       references DISCIPLINA;
	   
alter table DISCCURR 
       add constraint FKC_DISCCURR_CURRICULO
       foreign key (CURRICULO_FK) 
       references CURRICULO;
	   
alter table CURRICULO 
       add constraint FKC_CURRICULO_CURSO
       foreign key (CURSO_FK) 
       references CURSO;

alter table CALOUROS_CURSO
	add constraint FKC_CURSO
	foreign key (CURSO_FK)
	references CURSO;

alter table CALOUROS_CURSO
	add constraint FKC_CALOUROS
	foreign key (CALOUROS_FK)
	references CALOUROS;

alter table PEDIDOS_COORDENADORES
	add constraint FKC_CURSO
	foreign key (CURSO_FK)
	references CURSO;
	
alter table VAGAS_SOLICITADAS
	add constraint FKC_DISCIPLINA
	foreign key (DISCIPLINA_FK)
	references DISCIPLINA;

alter table VAGAS_SOLICITADAS
	add constraint FKC_PEDIDOS_COORDENADORES
	foreign key (PEDIDOS_COORDENADORES_FK)
	references PEDIDOS_COORDENADORES;

alter table VAGAS_ATENDIDAS
	add constraint FKC_DISCIPLINA
	foreign key (DISCIPLINA_FK)
	references DISCIPLINA;

alter table VAGAS_ATENDIDAS
	add constraint FKC_PEDIDOS_COORDENADORES
	foreign key (PEDIDOS_COORDENADORES_FK)
	references PEDIDOS_COORDENADORES;

alter table PERIODO
	add constraint FKC_CURSO
	foreign key (CURSO_FK)
	references CURSO;
