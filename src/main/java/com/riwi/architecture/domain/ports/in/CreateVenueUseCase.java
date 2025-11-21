package com.riwi.architecture.domain.ports.in;

import com.riwi.architecture.domain.model.Venue;

public interface CreateVenueUseCase {
    Venue createVenue(Venue venue);
}