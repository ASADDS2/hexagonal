package com.riwi.architecture.domain.exceptions;

public class InsufficientTicketsException extends RuntimeException {
    public InsufficientTicketsException(String message) {
        super(message);
    }

    public InsufficientTicketsException(String message, Throwable cause) {
        super(message, cause);
    }

    public InsufficientTicketsException(int requested, int available) {
        super("Insufficient tickets available. Requested: " + requested + ", Available: " + available);
    }
}
