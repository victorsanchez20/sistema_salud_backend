package com.salud.demo.exception;

/**
 * Se lanza cuando un recurso solicitado (paciente, doctor, cita, etc.) no existe.
 * Extiende RuntimeException para mantener la compatibilidad con el código que
 * ya capturaba RuntimeException (AuthController, HistoriaClinicaController).
 */
public class RecursoNoEncontradoException extends RuntimeException {

    public RecursoNoEncontradoException(String mensaje) {
        super(mensaje);
    }

    /** Crea el mensaje estándar "<recurso> no encontrado". */
    public static RecursoNoEncontradoException de(String recurso) {
        return new RecursoNoEncontradoException(recurso + " no encontrado");
    }
}
