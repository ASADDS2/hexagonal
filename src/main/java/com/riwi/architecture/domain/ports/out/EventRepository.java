package com.riwi.architecture.domain.ports.out;

import com.riwi.architecture.domain.model.Event;

import java.util.Optional;

/**
 * Puerto de salida (OUT PORT) - Repositorio de Event
 * Define las operaciones de persistencia que necesita el dominio
 * DOMINIO PURO - Sin dependencias de frameworks
 * La implementación estará en la capa de INFRASTRUCTURE
 */
public interface EventRepository {
    
    /**
     * Guardar un evento (crear o actualizar)
     */
    Event save(Event event);
    
    /**
     * Buscar un evento por su ID
     */
    Optional<Event> findById(Long id);
    
    /**
     * Eliminar un evento por su ID
     */
    void deleteById(Long id);
    
    /**
     * Verificar si un evento existe por ID
     */
    boolean existsById(Long id);
}
