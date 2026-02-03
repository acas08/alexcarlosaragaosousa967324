CREATE TABLE IF NOT EXISTS tb_album_imagem (
                                                id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                                                album_id BIGINT NOT NULL REFERENCES tb_album(id),
                                                chave_registro VARCHAR(500) NOT NULL,
                                                nome_original VARCHAR(255),
                                                content_type VARCHAR(120),
                                                size_bytes BIGINT,
                                                data_cricao TIMESTAMP NOT NULL DEFAULT NOW()
    );