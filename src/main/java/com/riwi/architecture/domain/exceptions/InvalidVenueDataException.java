package com.riwi.architecture.domain.exceptions;

public class InvalidVenueDataException extends RuntimeException {
    public InvalidVenueDataException(String message) {
        super(message);
    }

    public InvalidVenueDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidVenueDataException(String field, String reason) {
        super("Invalid venue data for field '" + field + "': " + reason);
    }
}
