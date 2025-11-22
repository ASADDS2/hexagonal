package com.riwi.architecture.application.usecase;

import com.riwi.architecture.domain.model.Event;
import com.riwi.architecture.domain.ports.in.UpdateEventUseCase;
import com.riwi.architecture.domain.ports.out.EventRepository;
import com.riwi.architecture.domain.exceptions.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * Implementación del caso de uso: Actualizar Event
 * CAPA DE APLICACIÓN - Contiene lógica de negocio
 */
@Service
@Transactional
public class UpdateEventUseCaseImpl implements UpdateEventUseCase {
    
    private final EventRepository eventRepository;

    public UpdateEventUseCaseImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public Event updateEvent(Long id, Event event) {
        validateId(id);
        validateEventForUpdate(event);
        
        Event existingEvent = eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException(id));
        
        // LÓGICA DE NEGOCIO: Actualizar solo campos permitidos
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
                .orElseThrow(() -> new EventNotFoundException(eventId));
        
        // ========== LÓGICA DE NEGOCIO: VENDER TICKETS ==========
        
        // Validación 1: Cantidad válida
        if (quantity == null || quantity <= 0) {
            throw new InvalidEventDataException("quantity", "Needs to be greater than zero");
        }
        
        // Validación 2: Evento activo
        if (event.getActive() == null || !event.getActive()) {
            throw new EventInactiveException(eventId);
        }
        
        // Validación 3: Tickets disponibles
        if (event.getAvailableTickets() == null || event.getAvailableTickets() <= 0) {
            throw new InsufficientTicketsException(0, quantity);
        }
        
        // Validación 4: Suficientes tickets
        if (event.getAvailableTickets() < quantity) {
            throw new InsufficientTicketsException(event.getAvailableTickets(), quantity);
        }
        
        // Validación 5: Evento no pasado
        if (isEventPast(event)) {
            throw new PastEventException(eventId);
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
                .orElseThrow(() -> new EventNotFoundException(eventId));
        
        // ========== LÓGICA DE NEGOCIO: DEVOLVER TICKETS ==========
        
        // Validación 1: Cantidad válida
        if (quantity == null || quantity <= 0) {
            throw new InvalidEventDataException("quantity", "Needs to be greater than zero");
        }
        
        // Validación 2: No exceder capacidad total
        int newAvailable = event.getAvailableTickets() + quantity;
        if (newAvailable > event.getTotalCapacity()) {
            throw new InvalidEventDataException("refund", 
                "Refund exceeds total capacity of the event");
        }
        
        // Acción: Incrementar tickets disponibles
        event.setAvailableTickets(newAvailable);
        event.setUpdatedAt(LocalDateTime.now());
        
        return eventRepository.save(event);
    }
    
    // ==================== MÉTODOS AUXILIARES ====================
    
    private boolean isEventPast(Event event) {
        if (event.getEventEndDate() != null) {
            return LocalDateTime.now().isAfter(event.getEventEndDate());
        }
        return event.getEventDate() != null && 
               LocalDateTime.now().isAfter(event.getEventDate());
    }
    
    private void validateEventForUpdate(Event event) {
        if (event == null) {
            throw new InvalidEventDataException("event", "Cant be null");
        }
        
        if (event.getName() == null || event.getName().trim().isEmpty()) {
            throw new InvalidEventDataException("name", "Is mandatory");
        }
        
        if (event.getEventDate() == null) {
            throw new InvalidEventDataException("eventDate", "Is mandatory");
        }
        
        if (event.getTicketPrice() == null || event.getTicketPrice().doubleValue() < 0) {
            throw new InvalidEventDataException("ticketPrice", "Needs to be zero or positive");
        }
    }
    
    private void validateId(Long id) {
        if (id == null || id <= 0) {
            throw new InvalidEventDataException("id", "Needs to be a positive number");
        }
    }
}