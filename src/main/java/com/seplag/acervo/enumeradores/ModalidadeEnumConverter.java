package com.seplag.acervo.enumeradores;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class ModalidadeEnumConverter implements AttributeConverter<ModalidadeEnum, Integer> {

    @Override
    public Integer convertToDatabaseColumn(ModalidadeEnum attribute) {
        return attribute == null ? null : attribute.getValor();
    }

    @Override
    public ModalidadeEnum convertToEntityAttribute(Integer dbData) {
        return ModalidadeEnum.fromValor(dbData);
    }
}
