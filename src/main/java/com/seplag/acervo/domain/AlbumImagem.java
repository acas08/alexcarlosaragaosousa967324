package com.seplag.acervo.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "tb_album_imagem")
public class AlbumImagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "album_id")
    private Album album;

    @Column(name = "chave_registro")
    private String chaveRegistro;

    @Column(name = "nome_original")
    private String nomeOrginal;

    @Column(name = "content_type")
    private String contentType;

    @Column(name = "size_bytes")
    private Long sizeBytes;

    @Column(name = "data_cricao", nullable = false)
    private LocalDateTime dataCriacao;

}
