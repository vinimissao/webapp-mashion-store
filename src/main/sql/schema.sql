
CREATE TABLE usuarios (
                          id INT PRIMARY KEY AUTO_INCREMENT,
                          email VARCHAR(100) NOT NULL UNIQUE,
                          senha VARCHAR(100) NOT NULL,
                          nome VARCHAR(100),
                          telefone VARCHAR(20),
                          data_nascimento DATE,
                          is_admin BOOLEAN DEFAULT FALSE,
                          data_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE clientes (
                          id INT PRIMARY KEY AUTO_INCREMENT,
                          usuario_id INT NOT NULL,
                          logradouro VARCHAR(150),
                          numero VARCHAR(10),
                          bairro VARCHAR(100),
                          cidade VARCHAR(100),
                          estado VARCHAR(2),
                          cep VARCHAR(10),
                          FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);
CREATE TABLE administradores (
                                 id INT PRIMARY KEY AUTO_INCREMENT,
                                 usuario_id INT NOT NULL,
                                 nivel_acesso INT DEFAULT 1, -- por exemplo, nível de acesso para controlar permissões
                                 FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);
CREATE TABLE produtos (
                          id INT PRIMARY KEY AUTO_INCREMENT,
                          nome VARCHAR(100) NOT NULL,
                          descricao TEXT,
                          preco DECIMAL(10, 2) NOT NULL,
                          estoque INT NOT NULL,
                          data_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
ALTER TABLE produtos
    ADD COLUMN imagem BLOB;
CREATE TABLE pedidos (
                         id INT PRIMARY KEY AUTO_INCREMENT,
                         cliente_id INT NOT NULL,
                         data_pedido TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         total DECIMAL(10, 2),
                         status VARCHAR(20),
                         FOREIGN KEY (cliente_id) REFERENCES clientes(id)
);
CREATE TABLE itens_pedido (
                              id INT PRIMARY KEY AUTO_INCREMENT,
                              pedido_id INT NOT NULL,
                              produto_id INT NOT NULL,
                              quantidade INT NOT NULL,
                              preco DECIMAL(10, 2) NOT NULL,
                              FOREIGN KEY (pedido_id) REFERENCES pedidos(id),
                              FOREIGN KEY (produto_id) REFERENCES produtos(id)
);
select * from usuarios;
select* from clientes;
select* from administradores;
ALTER TABLE clientes MODIFY estado VARCHAR(50);