package com.seplag.acervo.enumeradores;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ModalidadeEnumTest {

    @Test
    void fromValor() {
        assertEquals(ModalidadeEnum.CANTOR, ModalidadeEnum.fromValor(0));
    }

    @Test
    void getValor() {
        assertEquals(0, ModalidadeEnum.CANTOR.getValor());
    }

    @Test
    void getDescricao() {
        assertEquals("Cantor", ModalidadeEnum.CANTOR.getDescricao());
    }
}