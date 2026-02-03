CREATE TABLE IF NOT EXISTS tb_regional (
                                           id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                                           codigo BIGINT NOT NULL,
                                           nome VARCHAR(255) NOT NULL,
                                           ativo BOOLEAN NOT NULL DEFAULT TRUE
    );


INSERT INTO tb_regional (codigo, nome, ativo) VALUES
                                              (50, 'REGIONAL DE TESTE PARA INATIVAR - NÂO EXISTE NO ENDPOINT', true),
                                              ( 33, 'REGIONAL DE TESTE PARA ALTERAR - EXISTE NO ENDPOINT', true);                                          ;