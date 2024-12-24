CREATE TABLE IF NOT EXISTS autores (
    id uuid NOT NULL,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    descricao VARCHAR(400) NOT NULL,
    criado_em TIMESTAMP(6) WITH TIME ZONE,
    CONSTRAINT pk_autores PRIMARY KEY (id),
    CONSTRAINT uk_autores_email UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS categorias (
    id uuid NOT NULL,
    nome VARCHAR(255) NOT NULL,
    CONSTRAINT pk_categorias PRIMARY KEY (id),
    CONSTRAINT uk_categorias_nome UNIQUE (nome)
);

CREATE TABLE IF NOT EXISTS livros (
    id uuid NOT NULL,
    titulo VARCHAR(255) NOT NULL,
    resumo VARCHAR(500) NOT NULL,
    sumario TEXT NOT NULL,
    preco NUMERIC(38, 2) NOT NULL CHECK (preco >= 20),
    paginas INTEGER NOT NULL CHECK (paginas >= 100),
    isbn VARCHAR(255) NOT NULL,
    data_publicacao date NOT NULL,
    autor_id uuid NOT NULL,
    categoria_id uuid NOT NULL,
    CONSTRAINT pk_livros PRIMARY KEY (id)
);

ALTER TABLE IF EXISTS livros
ADD CONSTRAINT fk_livros_autores FOREIGN key (autor_id) REFERENCES autores;

ALTER TABLE IF EXISTS livros
ADD CONSTRAINT fk_livros_categorias FOREIGN key (categoria_id) REFERENCES categorias;

CREATE TABLE IF NOT EXISTS paises (
    id uuid NOT NULL,
    nome VARCHAR(255) NOT NULL,
    CONSTRAINT pk_paises PRIMARY KEY (id),
    CONSTRAINT uk_paises_nome UNIQUE (nome)
);

CREATE TABLE IF NOT EXISTS estados (
    id uuid NOT NULL,
    nome VARCHAR(255) NOT NULL,
    pais_id uuid NOT NULL,
    CONSTRAINT pk_estados PRIMARY KEY (id),
    CONSTRAINT uk_estados_nome UNIQUE (nome)
);

ALTER TABLE IF EXISTS estados
ADD CONSTRAINT fk_estados_paises FOREIGN key (pais_id) REFERENCES paises;

CREATE TABLE IF NOT EXISTS compras (
    id uuid NOT NULL,
    nome VARCHAR(255) NOT NULL,
    sobrenome VARCHAR(255) NOT NULL,
    documento VARCHAR(255) NOT NULL,
    telefone VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    cep VARCHAR(255) NOT NULL,
    endereco VARCHAR(255) NOT NULL,
    complemento VARCHAR(255) NOT NULL,
    pais_id uuid NOT NULL,
    estado_id uuid,
    cidade VARCHAR(255) NOT NULL,
    CONSTRAINT pk_compras PRIMARY KEY (id)
);

ALTER TABLE IF EXISTS compras
ADD CONSTRAINT fk_compras_paises FOREIGN key (pais_id) REFERENCES paises;

ALTER TABLE IF EXISTS compras
ADD CONSTRAINT fk_compras_estados FOREIGN key (estado_id) REFERENCES estados;

CREATE TABLE IF NOT EXISTS pedidos (
    compra_id uuid NOT NULL,
    CONSTRAINT pk_pedidos PRIMARY KEY (compra_id)
);

ALTER TABLE IF EXISTS pedidos
ADD CONSTRAINT fk_pedidos_compras FOREIGN key (compra_id) REFERENCES compras;

CREATE TABLE IF NOT EXISTS pedidos_itens (
    pedido_id uuid NOT NULL,
    livro_id uuid NOT NULL,
    quantidade INTEGER,
    preco_unitario NUMERIC(38, 2)
);

ALTER TABLE IF EXISTS pedidos_itens
ADD CONSTRAINT fk_pedidos_itens_pedidos FOREIGN key (pedido_id) REFERENCES pedidos;

ALTER TABLE IF EXISTS pedidos_itens
ADD CONSTRAINT fk_pedidos_itens_livros FOREIGN key (livro_id) REFERENCES livros;
