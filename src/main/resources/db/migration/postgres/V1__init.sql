-- SEQUENCE: hibernate_sequence

-- DROP SEQUENCE hibernate_sequence;

CREATE SEQUENCE hibernate_sequence
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

ALTER SEQUENCE hibernate_sequence
    OWNER TO postgres;


-- Table: empresas

-- DROP TABLE empresas;

CREATE TABLE empresas
(
    id serial NOT NULL,
    data_criacao timestamp without time zone NOT NULL,
    data_atualizacao timestamp without time zone,
    cnpj character varying(255) COLLATE pg_catalog."default" NOT NULL,
    razao_social character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT empresas_pkey PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE empresas
    OWNER to postgres;
    
    
    

-- Table: funcionarios

-- DROP TABLE funcionarios;

CREATE TABLE funcionarios
(
    id serial NOT NULL,
    data_criacao timestamp without time zone NOT NULL,
    data_atualizacao timestamp without time zone,
    cpf character varying(255) COLLATE pg_catalog."default" NOT NULL,
    email character varying(255) COLLATE pg_catalog."default",
    nome character varying(255) COLLATE pg_catalog."default" NOT NULL,
    perfil character varying(255) COLLATE pg_catalog."default" NOT NULL,
    qtd_horas_almoco real,
    qtd_horas_trabalho_dia real,
    senha character varying(255) COLLATE pg_catalog."default" NOT NULL,
    valor_hora numeric(19,2),
    empresa_id bigint,
    CONSTRAINT funcionarios_pkey PRIMARY KEY (id),
    CONSTRAINT fk15hs4n2yk287naj2tgawfi8mv FOREIGN KEY (empresa_id)
        REFERENCES empresas (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE funcionarios
    OWNER to postgres;
    
    
    
-- Table: lancamentos

-- DROP TABLE lancamentos;

CREATE TABLE lancamentos
(
    id serial NOT NULL,
    data_criacao timestamp without time zone NOT NULL,
    data_atualizacao timestamp without time zone,
    data timestamp without time zone NOT NULL,
    descricao character varying(255) COLLATE pg_catalog."default",
    localizacao character varying(255) COLLATE pg_catalog."default",
    tipo character varying(255) COLLATE pg_catalog."default" NOT NULL,
    funcionario_id bigint,
    CONSTRAINT lancamentos_pkey PRIMARY KEY (id),
    CONSTRAINT fk78ho4wcapij1813wh86i1hhk6 FOREIGN KEY (funcionario_id)
        REFERENCES funcionarios (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE lancamentos
    OWNER to postgres;