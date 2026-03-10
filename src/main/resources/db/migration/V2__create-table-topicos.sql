CREATE TABLE topicos (
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    titulo     VARCHAR(200) NOT NULL,
    mensagem   TEXT         NOT NULL,
    data_criacao DATETIME   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    status     VARCHAR(50)  NOT NULL DEFAULT 'ABERTO',
    autor_id   BIGINT       NOT NULL,
    curso      VARCHAR(100) NOT NULL,

    CONSTRAINT fk_topicos_usuario FOREIGN KEY (autor_id) REFERENCES usuarios (id)
);
