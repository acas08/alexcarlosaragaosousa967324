CREATE TABLE IF NOT EXISTS tb_artista (
                                          id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                                          descricao VARCHAR(255) NOT NULL,
                                          modalidade SMALLINT NOT NULL CHECK (modalidade IN (0, 1))
    );

CREATE TABLE IF NOT EXISTS tb_album (
                                        id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                                        descricao VARCHAR(255) NOT NULL
    );

CREATE TABLE IF NOT EXISTS tb_artista_album (
                                                artista_id BIGINT REFERENCES tb_artista(id),
    album_id BIGINT REFERENCES tb_album(id),
    PRIMARY KEY (artista_id, album_id)
    );

INSERT INTO tb_artista (descricao, modalidade) VALUES
                                       ('Serj Tankian', 0),
                                       ( 'Mike Shinoda', 0),
                                       ( 'Michel Teló', 0),
                                       ( 'Guns N’ Roses', 1);

INSERT INTO tb_album (descricao) VALUES
                                     ( 'Harakiri'),
                                     ( 'Black Blooms'),
                                     ( 'The Rough Dog'),
                                     ( 'The Rising Tied'),
                                     ( 'Post Traumatic'),
                                     ( '“Where’d You Go'),
                                     ( 'Post Traumatic EP'),
                                     ( 'Bem Sertanejo'),
                                     ( 'Bem Sertanejo - O Show (Ao Vivo)'),
                                     ( 'Bem Sertanejo - (1ª Temporada) - EP'),
                                     ( 'Use Your Illusion I'),
                                     ( 'Use Your Illusion II'),
                                     ( 'Greatest Hits');

INSERT INTO tb_artista_album (artista_id, album_id) VALUES (1, 1);
INSERT INTO tb_artista_album (artista_id, album_id) VALUES (1, 2);
INSERT INTO tb_artista_album (artista_id, album_id) VALUES (1, 3);
INSERT INTO tb_artista_album (artista_id, album_id) VALUES (2, 4);
INSERT INTO tb_artista_album (artista_id, album_id) VALUES (2, 5);
INSERT INTO tb_artista_album (artista_id, album_id) VALUES (2, 6);
INSERT INTO tb_artista_album (artista_id, album_id) VALUES (2, 7);
INSERT INTO tb_artista_album (artista_id, album_id) VALUES (3, 8);
INSERT INTO tb_artista_album (artista_id, album_id) VALUES (3, 9);
INSERT INTO tb_artista_album (artista_id, album_id) VALUES (3, 10);
INSERT INTO tb_artista_album (artista_id, album_id) VALUES (4, 11);
INSERT INTO tb_artista_album (artista_id, album_id) VALUES (4, 12);
INSERT INTO tb_artista_album (artista_id, album_id) VALUES (4, 13);