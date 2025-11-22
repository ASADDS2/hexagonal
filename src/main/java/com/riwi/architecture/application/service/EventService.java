package com.riwi.architecture.application.service;

import com.riwi.architecture.domain.model.Event;
import com.riwi.architecture.domain.ports.in.*;
import com.riwi.architecture.domain.ports.out.EventRepository;
import com.riwi.architecture.domain.exceptions.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * Servicio de aplicación para Event
 * AQUÍ va toda la LÓGICA DE NEGOCIO
 * Implementa los casos de uso del dominio
 */
@Service
@Transactional
public class EventService implements 
        CreateEventUseCase,
        UpdateEventUseCase,
        DeleteEventUseCase {
    
    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    // ==================== CREATE ====================
    
    @Override
    public Event createEvent(Event event) {
        // LÓGICA DE NEGOCIO: Validaciones
        validateEventForCreation(event);
        
        // LÓGICA DE NEGOCIO: Establecer valores por defecto
        event.setActive(true);
        event.setCreatedAt(LocalDateTime.now());
        event.setUpdatedAt(LocalDateTime.now());
        
        // LÓGICA DE NEGOCIO: availableTickets debe ser igual a totalCapacity al crear
        if (event.getTotalCapacity() != null) {
            event.setAvailableTickets(event.getTotalCapacity());
        }
        
        return eventRepository.save(event);
    }
    
    // ==================== UPDATE ====================
    
    @Override
    public Event updateEvent(Long id, Event event) {
        validateId(id);
        validateEventForUpdate(event);
        
        Event existingEvent = eventRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Event not found " + id));
        
        // LÓGICA DE NEGOCIO: Actualizar campos permitidos
        existingEvent.setName(event.getName());
        existingEvent.setDescription(event.getDescription());
        existingEvent.setEventDate(event.getEventDate());
        existingEvent.setEventEndDate(event.getEventEndDate());
        existingEvent.setCategory(event.getCategory());
        existingEvent.setTicketPrice(event.getTicketPrice());
        existingEvent.setUpdatedAt(LocalDateTime.now());
        
        return eventRepository.save(existingEvent);
    }
    
    @Override
    public Event sellTickets(Long eventId, Integer quantity) {
        validateId(eventId);
        
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Event not found " + eventId));
        
        // ========== LÓGICA DE NEGOCIO: VENDER TICKETS ==========
        
        // Validación 1: Cantidad válida
        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }
        
        // Validación 2: Evento activo y con tickets disponibles
        if (event.getActive() == null || !event.getActive()) {
            throw new IllegalArgumentException("Event is inactive");
        }
        
        if (event.getAvailableTickets() == null || event.getAvailableTickets() <= 0) {
            throw new IllegalArgumentException("No tickets available for this event");
        }
        
        // Validación 3: Suficientes tickets
        if (event.getAvailableTickets() < quantity) {
            throw new IllegalArgumentException(
                "No enough tickets availables: This is the quantity " + 
                event.getAvailableTickets());
        }
        
        // Validación 4: Evento no pasado
        if (isEventPast(event)) {
            throw new IllegalArgumentException("Event has already passed");
        }
        
        // Acción: Reducir tickets disponibles
        event.setAvailableTickets(event.getAvailableTickets() - quantity);
        event.setUpdatedAt(LocalDateTime.now());
        
        return eventRepository.save(event);
    }
    
    @Override
    public Event refundTickets(Long eventId, Integer quantity) {
        validateId(eventId);
        
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Event not found " + eventId));
        
        // ========== LÓGICA DE NEGOCIO: DEVOLVER TICKETS ==========
        
        // Validación 1: Cantidad válida
        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }
        
        // Validación 2: No exceder capacidad total
        int newAvailable = event.getAvailableTickets() + quantity;
        if (newAvailable > event.getTotalCapacity()) {
            throw new IllegalArgumentException(
                "Refund exceeds total capacity of the event");
        }
        
        // Acción: Incrementar tickets disponibles
        event.setAvailableTickets(newAvailable);
        event.setUpdatedAt(LocalDateTime.now());
        
        return eventRepository.save(event);
    }
    
    // ==================== DELETE ====================
    
    @Override
    public void deleteEvent(Long id) {
        validateId(id);
        
        if (!eventRepository.existsById(id)) {
            throw new IllegalArgumentException("Event not found " + id);
        }
        
        eventRepository.deleteById(id);
    }
    
    // ==================== MÉTODOS AUXILIARES (LÓGICA DE NEGOCIO) ====================
    
    /**
     * LÓGICA DE NEGOCIO: Verificar si el evento ya pasó
     */
    private boolean isEventPast(Event event) {
        if (event.getEventEndDate() != null) {
            return LocalDateTime.now().isAfter(event.getEventEndDate());
        }
        return event.getEventDate() != null && 
               LocalDateTime.now().isAfter(event.getEventDate());
    }
    
    /**
     * LÓGICA DE NEGOCIO: Validar evento para creación
     */
    private void validateEventForCreation(Event event) {
        if (event == null) {
            throw new IllegalArgumentException("Event cant be null");
        }
        
        validateEventBasicFields(event);
        
        if (event.getTotalCapacity() == null || event.getTotalCapacity() <= 0) {
            throw new IllegalArgumentException(
                "Total capacity must be greater than zero");
        }
        
        if (event.getVenueId() == null) {
            throw new IllegalArgumentException("Venue ID is mandatory");
        }
    }
    
    /**
     * LÓGICA DE NEGOCIO: Validar evento para actualización
     */
    private void validateEventForUpdate(Event event) {
        if (event == null) {
            throw new IllegalArgumentException("Event cant be null");
        }
        
        validateEventBasicFields(event);
    }
    
    /**
     * LÓGICA DE NEGOCIO: Validaciones básicas de campos
     */
    private void validateEventBasicFields(Event event) {
        if (event.getName() == null || event.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("The name is mandatory");
        }
        
        if (event.getEventDate() == null) {
            throw new IllegalArgumentException("The event date is mandatory");
        }
        
        if (event.getTicketPrice() == null || 
            event.getTicketPrice().doubleValue() < 0) {
            throw new IllegalArgumentException(
                "The ticket price needs to be zero or positive");
        }
    }
    
    /**
     * Validar ID
     */
    private void validateId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("The ID must be a positive value");
        }
    }
}
