package com.riwi.architecture.domain.ports.out;

import com.riwi.architecture.domain.model.Venue;

import java.util.Optional;

/**
 * Puerto de salida (OUT PORT) - Repositorio de Venue
 * Define las operaciones de persistencia que necesita el dominio
 * DOMINIO PURO - Sin dependencias de frameworks
 * La implementación estará en la capa de INFRASTRUCTURE
 */
public interface VenueRepository {
    
    /**
     * Guardar un venue (crear o actualizar)
     */
    Venue save(Venue venue);
    
    /**
     * Buscar un venue por su ID
     */
    Optional<Venue> findById(Long id);
    
    /**
     * Eliminar un venue por su ID
     */
    void deleteById(Long id);
    
    /**
     * Verificar si un venue existe por ID
     */
    boolean existsById(Long id);
}
