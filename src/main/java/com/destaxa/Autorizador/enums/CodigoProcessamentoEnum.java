package com.destaxa.Autorizador.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum CodigoProcessamentoEnum {

    CREDITO_A_VISTA("003000", "Credito a vista"),
    CREDITO_PARCELADO("003001", "Credito Parcelado");

    private final String code;
    private final String description;


    public static CodigoProcessamentoEnum toEnum(String code) {
        return Arrays.stream(CodigoProcessamentoEnum.values())
                .filter(ctc -> ctc.getCode().equals(code))
                .findFirst()
                .orElse(null);
    }
}
