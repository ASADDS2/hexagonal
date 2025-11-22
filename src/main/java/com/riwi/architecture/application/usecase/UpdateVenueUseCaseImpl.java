package com.riwi.architecture.application.usecase;


import com.riwi.architecture.domain.model.Venue;
import com.riwi.architecture.domain.ports.in.UpdateVenueUseCase;
import com.riwi.architecture.domain.ports.out.VenueRepository;
import com.riwi.architecture.domain.exceptions.InvalidVenueDataException;
import com.riwi.architecture.domain.exceptions.VenueNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * Implementación del caso de uso: Actualizar Venue
 * CAPA DE APLICACIÓN - Contiene lógica de negocio
 */
@Service
@Transactional
public class UpdateVenueUseCaseImpl implements UpdateVenueUseCase {
    
    private final VenueRepository venueRepository;

    public UpdateVenueUseCaseImpl(VenueRepository venueRepository) {
        this.venueRepository = venueRepository;
    }

    @Override
    public Venue updateVenue(Long id, Venue venue) {
        validateId(id);
        validateVenueForUpdate(venue);
        
        Venue existingVenue = venueRepository.findById(id)
                .orElseThrow(() -> new VenueNotFoundException(id));
        
        // LÓGICA DE NEGOCIO: Actualizar solo campos permitidos
        existingVenue.setName(venue.getName());
        existingVenue.setAddress(venue.getAddress());
        existingVenue.setCity(venue.getCity());
        existingVenue.setCountry(venue.getCountry());
        existingVenue.setCapacity(venue.getCapacity());
        existingVenue.setType(venue.getType());
        existingVenue.setFacilities(venue.getFacilities());
        existingVenue.setUpdatedAt(LocalDateTime.now());
        
        return venueRepository.save(existingVenue);
    }
    
    // ==================== VALIDACIONES ====================
    
    private void validateVenueForUpdate(Venue venue) {
        if (venue == null) {
            throw new InvalidVenueDataException("venue", "cant be null");
        }
        
        if (venue.getName() == null || venue.getName().trim().isEmpty()) {
            throw new InvalidVenueDataException("name", "is mandatory");
        }
        
        if (venue.getAddress() == null || venue.getAddress().trim().isEmpty()) {
            throw new InvalidVenueDataException("address", "is mandatory");
        }
        
        if (venue.getCity() == null || venue.getCity().trim().isEmpty()) {
            throw new InvalidVenueDataException("city", "is mandatory");
        }
        
        if (venue.getCountry() == null || venue.getCountry().trim().isEmpty()) {
            throw new InvalidVenueDataException("country", "is mandatory");
        }
    }
    
    private void validateId(Long id) {
        if (id == null || id <= 0) {
            throw new InvalidVenueDataException("id", "must be a positive value");
        }
    }
}