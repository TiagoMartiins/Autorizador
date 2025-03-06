package com.destaxa.Autorizador.exception;

public class BusinessException extends Exception{

    private static final long serialVersionUID = 1L;

    public BusinessException(String message){
        super(message);
    }

    public BusinessException(String message, Object ... parametros) {
        super(String.format(message, parametros));
    }
}
