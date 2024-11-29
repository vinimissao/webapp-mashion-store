
CREATE TABLE `usuarios` (
                            `id` INT AUTO_INCREMENT PRIMARY KEY,
                            `cpf` VARCHAR(11) NOT NULL UNIQUE,
                            `nome` VARCHAR(100) NOT NULL,
                            `email` VARCHAR(100) NOT NULL UNIQUE,
                            `logradouro` VARCHAR(255) NOT NULL,
                            `cidade` VARCHAR(100) NOT NULL,
                            `estado` VARCHAR(50) NOT NULL,
                            `bairro` VARCHAR(100) NOT NULL,
                            `numero` VARCHAR(10) NOT NULL,
                            `cep` VARCHAR(8) NOT NULL,
                            `telefone` VARCHAR(15) NOT NULL,
                            `data_nasc` DATE NOT NULL,
                            `senha` VARCHAR(255) NOT NULL,
                            `is_admin` BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE `administradores` (
                                   `id` INT AUTO_INCREMENT PRIMARY KEY,
                                   `usuario_id` INT NOT NULL,
                                   `nivel_acesso` VARCHAR(50) NOT NULL,
                                   FOREIGN KEY (`usuario_id`) REFERENCES `usuarios`(`id`) ON DELETE CASCADE
);


CREATE TABLE `clientes` (
                            `id` INT AUTO_INCREMENT PRIMARY KEY,
                            `usuario_id` INT NOT NULL,
                            FOREIGN KEY (`usuario_id`) REFERENCES `usuarios`(`id`) ON DELETE CASCADE
);

CREATE TABLE `produtos` (
                            `id` INT AUTO_INCREMENT PRIMARY KEY,
                            `nome` VARCHAR(255) NOT NULL,
                            `descricao` TEXT NOT NULL,
                            `preco` DECIMAL(10, 2) NOT NULL,
                            `imagem` BLOB,
                            `estoque` INT NOT NULL,
                            `data_cadastro` TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE `pedidos` (
                           `id` INT AUTO_INCREMENT PRIMARY KEY,
                           `cliente_id` INT NOT NULL,
                           `data_pedido` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                           FOREIGN KEY (`cliente_id`) REFERENCES `clientes`(`id`) ON DELETE CASCADE
);

CREATE TABLE `itens_pedido` (
                                `id` INT AUTO_INCREMENT PRIMARY KEY,
                                `pedido_id` INT NOT NULL,
                                `produto_id` INT NOT NULL,
                                `quantidade` INT NOT NULL,
                                FOREIGN KEY (`pedido_id`) REFERENCES `pedidos`(`id`) ON DELETE CASCADE,
                                FOREIGN KEY (`produto_id`) REFERENCES `produtos`(`id`) ON DELETE CASCADE
);
