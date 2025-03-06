package com.destaxa.Autorizador.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.PropertyValueException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.FileNotFoundException;
import java.sql.SQLException;


@ControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class ResourceExceptionHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> validation(MethodArgumentNotValidException e, HttpServletRequest request){
        log.error("Erro ->",e);
        var err = new ValidationError(HttpStatus.BAD_REQUEST.value(),
                e.getMessage(), System.currentTimeMillis());

        e.getBindingResult().getFieldErrors().stream().forEach(x ->{
            err.addError(x.getField(), x.getDefaultMessage());
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<StandardError> validationRun(RuntimeException e, HttpServletRequest request) {
            log.error("Erro ->",e);
            var err = new StandardError(HttpStatus.SERVICE_UNAVAILABLE.value(),
                    e.getMessage(), System.currentTimeMillis());

            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(err);

    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<StandardError> validationConstraint(ConstraintViolationException e, HttpServletRequest request) {
        log.error("Erro ->",e);
        var err = new StandardError(HttpStatus.BAD_REQUEST.value(),
                e.getMessage(), System.currentTimeMillis());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(NonUniqueResultException.class)
    public ResponseEntity<StandardError> validationNonUnique(NonUniqueResultException e, HttpServletRequest request) {
        log.error("Erro ->",e);
        var err = new StandardError(HttpStatus.BAD_REQUEST.value(),
                e.getMessage(), System.currentTimeMillis());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<StandardError> validationNull(SQLException e, HttpServletRequest request) {
        log.error("Erro ->",e);
        var err = new StandardError(HttpStatus.BAD_REQUEST.value(),e.getMessage() , System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(DataAccessResourceFailureException.class)
    public ResponseEntity<StandardError> validationNull(DataAccessResourceFailureException e, HttpServletRequest request) {
        log.error("Erro ->",e);
        var err = new StandardError(HttpStatus.BAD_REQUEST.value(),e.getMessage() , System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<StandardError> validationFileNotFoun(FileNotFoundException e, HttpServletRequest request) {
        log.error("Erro ->",e);
        var err = new StandardError(HttpStatus.BAD_REQUEST.value(),e.getMessage() , System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(PropertyValueException.class)
    public ResponseEntity<StandardError> validationProperty(PropertyValueException e, HttpServletRequest request) {
        log.error("Erro ->",e);
        var err = new StandardError(HttpStatus.BAD_REQUEST.value(),e.getMessage() , System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<StandardError> validationNull(UnauthorizedException e, HttpServletRequest request) {
        log.error("Erro ->",e);
        var err = new StandardError(HttpStatus.UNAUTHORIZED.value(),e.getMessage() , System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(err);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<StandardError> validationNull(NullPointerException e, HttpServletRequest request) {
        log.error("Erro ->",e);
        var err = new StandardError(HttpStatus.BAD_REQUEST.value(),e.getMessage() , System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<StandardError> validationExpiration(JwtException e, HttpServletRequest request) {
        log.error("Erro -> ",e);
        var err = new StandardError(HttpStatus.FORBIDDEN.value(),e.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(err);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<StandardError> validationExpired(ExpiredJwtException e, HttpServletRequest request) {
        log.error("Erro -> ",e);
        var err = new StandardError(HttpStatus.FORBIDDEN.value(),e.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(err);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<StandardError> validationExpired(HttpRequestMethodNotSupportedException e, HttpServletRequest request) {
        log.error("Erro -> ",e);
        var err = new StandardError(HttpStatus.METHOD_NOT_ALLOWED.value(),e.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(err);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<StandardError> validationBusiness(BusinessException e, HttpServletRequest request) {
        log.error("Erro ->",e);
        var err = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }


}
