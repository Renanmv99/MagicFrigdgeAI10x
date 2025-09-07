CREATE TABLE food_item (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome varchar(255) NOT NULL,
    categoria varchar(100),
    quantidade INT NOT NULL,
    validade DATE NOT NULL
);