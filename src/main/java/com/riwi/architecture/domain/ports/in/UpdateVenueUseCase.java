package com.riwi.architecture.domain.ports.in;

import com.riwi.architecture.domain.model.Venue;

public interface UpdateVenueUseCase {
    Venue updateVenue(Long id, Venue venue);
}