-- Table: empresas

-- DROP TABLE empresas;

CREATE TABLE empresas
(
    id bigint NOT NULL,
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
    id bigint NOT NULL,
    data_criacao timestamp without time zone NOT NULL,
    data_atualizacao timestamp without time zone,
    cpf character varying(255) COLLATE pg_catalog."default" NOT NULL,
    email character varying(255) COLLATE pg_catalog."default",
    nome character varying(255) COLLATE pg_catalog."default" NOT NULL,
    perfil character varying(255) COLLATE pg_catalog."default" NOT NULL,
    qtd_horas_almoco real NOT NULL,
    qtd_horas_trabalho_dia real NOT NULL,
    senha character varying(255) COLLATE pg_catalog."default" NOT NULL,
    valor_hora numeric(19,2) NOT NULL,
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
    id bigint NOT NULL,
    data_criacao timestamp without time zone NOT NULL,
    data_atualizacao timestamp without time zone,
    data timestamp without time zone NOT NULL,
    descricao character varying(255) COLLATE pg_catalog."default" NOT NULL,
    localizacao character varying(255) COLLATE pg_catalog."default" NOT NULL,
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