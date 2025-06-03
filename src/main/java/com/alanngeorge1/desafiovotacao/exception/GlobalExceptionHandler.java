package com.alanngeorge1.desafiovotacao.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PautaNotFoundException.class)
    public ResponseEntity<Object> handlePautaFoundException(PautaNotFoundException ex){
        Map<String, Object> body = new HashMap<>();
        body.put("error", "Pauta não encontrada");
        body.put("message", ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException ex){
        Map<String, Object> body = new HashMap<>();
        body.put("error", "Erro Validação");
        body.put("message", ex.getBindingResult().getFieldError().getDefaultMessage());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception ex){
    Map<String, Object> body = new HashMap<>();
    body.put("error", "Erro interno");
    body.put("message", ex.getMessage());
    return new ResponseEntity<>(body,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
