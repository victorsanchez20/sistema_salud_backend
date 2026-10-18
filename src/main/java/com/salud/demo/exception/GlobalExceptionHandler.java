package com.salud.demo.exception;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Punto único de traducción de excepciones de dominio a respuestas HTTP.
 * Evita repetir bloques try/catch en cada controlador (DRY).
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<Map<String, String>> manejarNoEncontrado(RecursoNoEncontradoException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> manejarErrorGeneral(
        Exception ex) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("message", "Ocurrió un error interno."));
        }
    
}
