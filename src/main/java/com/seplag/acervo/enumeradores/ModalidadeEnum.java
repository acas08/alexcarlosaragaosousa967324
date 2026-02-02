package com.seplag.acervo.enumeradores;

import lombok.Getter;

@Getter
public enum ModalidadeEnum {
    CANTOR(0, "Cantor"),
    BANDA(1, "Banda");

    private final int valor;
    private final String descricao;

    ModalidadeEnum(int valor, String descricao) {
        this.valor = valor;
        this.descricao = descricao;
    }

}
