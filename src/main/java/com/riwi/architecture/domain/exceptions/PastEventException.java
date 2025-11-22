package com.riwi.architecture.domain.exceptions;


public class PastEventException extends RuntimeException {
    
    public PastEventException(Long eventId) {
        super("No se pueden vender tickets para el evento con ID " + eventId + " porque ya pasó");
    }
    
    public PastEventException(String message) {
        super(message);
    }
    
    public PastEventException(String message, Throwable cause) {
        super(message, cause);
    }
}
