package com.seplag.acervo.enumeradores;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ModalidadeEnumConverterTest {

    private final ModalidadeEnumConverter converter = new ModalidadeEnumConverter();


    @Test
    void convertToDatabaseColumn() {
        assertEquals(0, converter.convertToDatabaseColumn(ModalidadeEnum.CANTOR));
        assertEquals(1, converter.convertToDatabaseColumn(ModalidadeEnum.BANDA));
    }

    @Test
    void convertToEntityAttribute() {
        assertEquals(ModalidadeEnum.CANTOR, converter.convertToEntityAttribute(0));
        assertEquals(ModalidadeEnum.BANDA, converter.convertToEntityAttribute(1));
    }
}