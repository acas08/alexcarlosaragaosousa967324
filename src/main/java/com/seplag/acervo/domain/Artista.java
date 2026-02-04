package com.seplag.acervo.domain;

import com.seplag.acervo.enumeradores.ModalidadeEnum;
import com.seplag.acervo.enumeradores.ModalidadeEnumConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Table(name = "tb_artista")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Artista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String nome;

    @Convert(converter = ModalidadeEnumConverter.class)
    @Column(name = "modalidade", nullable = false)
    private ModalidadeEnum modalidade;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "artistas")
    private Set<Album> albuns;
}