package com.riwi.architecture.application.service;


import com.riwi.architecture.domain.model.Venue;
import com.riwi.architecture.domain.ports.in.*;
import com.riwi.architecture.domain.ports.out.VenueRepository;
import com.riwi.architecture.domain.exceptions.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * Servicio de aplicación para Venue
 * AQUÍ va toda la LÓGICA DE NEGOCIO
 * Implementa los casos de uso del dominio
 */
@Service
@Transactional
public class VenueService implements 
        CreateVenueUseCase,
        UpdateVenueUseCase,
        DeleteVenueUseCase {
    
    private final VenueRepository venueRepository;

    public VenueService(VenueRepository venueRepository) {
        this.venueRepository = venueRepository;
    }

    // ==================== CREATE ====================
    
    @Override
    public Venue createVenue(Venue venue) {
        // LÓGICA DE NEGOCIO: Validaciones
        validateVenueForCreation(venue);
        
        // LÓGICA DE NEGOCIO: Establecer valores por defecto
        venue.setActive(true);
        venue.setCreatedAt(LocalDateTime.now());
        venue.setUpdatedAt(LocalDateTime.now());
        
        return venueRepository.save(venue);
    }
    
    // ==================== UPDATE ====================
    
    @Override
    public Venue updateVenue(Long id, Venue venue) {
        validateId(id);
        validateVenueForUpdate(venue);
        
        Venue existingVenue = venueRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Venue no encontrado con ID: " + id));
        
        // LÓGICA DE NEGOCIO: Actualizar campos permitidos
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
    
    // ==================== DELETE ====================
    
    @Override
    public void deleteVenue(Long id) {
        validateId(id);
        
        if (!venueRepository.existsById(id)) {
            throw new IllegalArgumentException("Venue no encontrado con ID: " + id);
        }
        
        // TODO: LÓGICA DE NEGOCIO adicional
        // Verificar que no haya eventos activos asociados a este venue
        // antes de eliminarlo
        
        venueRepository.deleteById(id);
    }
    
    // ==================== MÉTODOS AUXILIARES (LÓGICA DE NEGOCIO) ====================
    
    /**
     * LÓGICA DE NEGOCIO: Validar venue para creación
     */
    private void validateVenueForCreation(Venue venue) {
        if (venue == null) {
            throw new IllegalArgumentException("El venue no puede ser nulo");
        }
        
        validateVenueBasicFields(venue);
        
        if (venue.getCapacity() == null || venue.getCapacity() <= 0) {
            throw new IllegalArgumentException("La capacidad debe ser mayor a cero");
        }
    }
    
    /**
     * LÓGICA DE NEGOCIO: Validar venue para actualización
     */
    private void validateVenueForUpdate(Venue venue) {
        if (venue == null) {
            throw new IllegalArgumentException("El venue no puede ser nulo");
        }
        
        validateVenueBasicFields(venue);
    }
    
    /**
     * LÓGICA DE NEGOCIO: Validaciones básicas de campos
     */
    private void validateVenueBasicFields(Venue venue) {
        if (venue.getName() == null || venue.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del venue es obligatorio");
        }
        
        if (venue.getAddress() == null || venue.getAddress().trim().isEmpty()) {
            throw new IllegalArgumentException("La dirección es obligatoria");
        }
        
        if (venue.getCity() == null || venue.getCity().trim().isEmpty()) {
            throw new IllegalArgumentException("La ciudad es obligatoria");
        }
        
        if (venue.getCountry() == null || venue.getCountry().trim().isEmpty()) {
            throw new IllegalArgumentException("El país es obligatorio");
        }
    }
    
    /**
     * Validar ID
     */
    private void validateId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID debe ser un valor positivo");
        }
    }
}
