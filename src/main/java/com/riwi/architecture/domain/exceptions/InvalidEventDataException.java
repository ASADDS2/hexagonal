package com.riwi.architecture.domain.exceptions;

public class InvalidEventDataException extends RuntimeException {
    public InvalidEventDataException(String message) {
        super(message);
    }

    public InvalidEventDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidEventDataException(String field, String reason) {
        super("Invalid event data for field '" + field + "': " + reason);
    }
}
