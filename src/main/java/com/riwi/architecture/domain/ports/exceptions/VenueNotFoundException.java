package com.riwi.architecture.domain.ports.exceptions;

public class VenueNotFoundException extends RuntimeException {
    public VenueNotFoundException(String message) {
        super(message);
    }

    public VenueNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public VenueNotFoundException(Long venueId) {
        super("Venue not found with ID: " + venueId);
    }
}
