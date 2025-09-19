CREATE TABLE endereco (
    id SERIAL PRIMARY KEY,
    bairro VARCHAR(255),
    cep VARCHAR(255),
    cidade VARCHAR(255),
    complemento VARCHAR(255),
    estado VARCHAR(255),
    logradouro VARCHAR(255),
    numero VARCHAR(255)
);

CREATE TABLE rendimento (
    id BIGSERIAL PRIMARY KEY,
    tipo_rendimento VARCHAR(255),
    valor_mensal NUMERIC(10, 2),
    cliente_id BIGINT,
    
    FOREIGN KEY (cliente_id) REFERENCES clientes(id)
);

CREATE TABLE usuarios (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255),
    email VARCHAR(255) UNIQUE NOT NULL,
    senha VARCHAR(255)
);

CREATE TABLE clientes (
    id BIGSERIAL PRIMARY KEY,
    profissao VARCHAR(100),
    cpf VARCHAR(11) UNIQUE,
    rg VARCHAR(20) UNIQUE,
    endereco_id BIGINT,

    FOREIGN KEY (id) REFERENCES usuarios(id),
    FOREIGN KEY (endereco_id) REFERENCES endereco(id)
);

CREATE TABLE veiculo (
    id SERIAL PRIMARY KEY,
    marca VARCHAR(255),
    modelo VARCHAR(255),
    placa VARCHAR(255),
    usuario_id BIGINT,
    
    CONSTRAINT fk_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios (id)
);
