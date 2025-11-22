package com.riwi.architecture.domain.exceptions;

public class EventNotFoundException extends RuntimeException {
    public EventNotFoundException(String message) {
        super(message);
    }

    public EventNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public EventNotFoundException(Long eventId) {
        super("Event not found with ID: " + eventId);
    }
}
