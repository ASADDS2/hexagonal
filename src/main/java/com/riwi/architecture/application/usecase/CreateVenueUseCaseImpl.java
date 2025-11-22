package com.riwi.architecture.application.usecase;

import com.riwi.architecture.domain.model.Venue;
import com.riwi.architecture.domain.ports.in.CreateVenueUseCase;
import com.riwi.architecture.domain.ports.out.VenueRepository;
import com.riwi.architecture.domain.exceptions.InvalidVenueDataException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional  

public class CreateVenueUseCaseImpl implements CreateVenueUseCase {

    private final VenueRepository venueRepository;
    public CreateVenueUseCaseImpl(VenueRepository venueRepository) {
        this.venueRepository = venueRepository;
    }

    @Override
    public Venue createVenue(Venue venue) {
        validateVenueForCreation(venue);
        
        venue.setActive(true);
        venue.setCreatedAt(LocalDateTime.now());
        venue.setUpdatedAt(LocalDateTime.now());
        return venueRepository.save(venue);
    }

    private void validateVenueForCreation(Venue venue) {
        if (venue == null) {
            throw new InvalidVenueDataException("venue", "no puede ser nulo");
        }
        
        if (venue.getName() == null || venue.getName().trim().isEmpty()) {
            throw new InvalidVenueDataException("name", "es obligatorio");
        }
        
        if (venue.getAddress() == null || venue.getAddress().trim().isEmpty()) {
            throw new InvalidVenueDataException("address", "es obligatoria");
        }
        
        if (venue.getCity() == null || venue.getCity().trim().isEmpty()) {
            throw new InvalidVenueDataException("city", "es obligatoria");
        }
        
        if (venue.getCountry() == null || venue.getCountry().trim().isEmpty()) {
            throw new InvalidVenueDataException("country", "es obligatorio");
        }
        
        if (venue.getCapacity() == null || venue.getCapacity() <= 0) {
            throw new InvalidVenueDataException("capacity", "debe ser mayor a cero");
        }
    }

}
