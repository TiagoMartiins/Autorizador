package com.destaxa.Autorizador.exception;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;


public class ValidationError extends StandardError{
    private static final long serialVersionUID = 1L;

    @Getter
    private List<FieldMessage> errors = new ArrayList<>();

    public ValidationError(Integer status, String msg, Long timestamp) {
        super(status, msg, timestamp);
    }

    public void addError(String fieldName, String message) {
        this.errors.add(new FieldMessage(fieldName, message));
    }
}