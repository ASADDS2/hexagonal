package com.riwi.architecture.domain.exceptions;

public class EventInactiveException extends RuntimeException {
    public EventInactiveException(String message) {
        super(message);
    }

    public EventInactiveException(String message, Throwable cause) {
        super(message, cause);
    }

    public EventInactiveException(Long eventId) {
        super("Event is inactive and cannot be accessed. Event ID: " + eventId);
    }
}
