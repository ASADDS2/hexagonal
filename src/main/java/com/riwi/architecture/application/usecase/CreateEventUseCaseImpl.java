package com.riwi.architecture.application.usecase;

import com.riwi.architecture.domain.model.Event;
import com.riwi.architecture.domain.ports.in.CreateEventUseCase;
import com.riwi.architecture.domain.ports.out.EventRepository;
import com.riwi.architecture.domain.exceptions.InvalidEventDataException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * Implementación del caso de uso: Crear Event
 * CAPA DE APLICACIÓN - Contiene lógica de negocio
 */
@Service
@Transactional
public class CreateEventUseCaseImpl implements CreateEventUseCase {
    
    private final EventRepository eventRepository;

    public CreateEventUseCaseImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public Event createEvent(Event event) {
        // LÓGICA DE NEGOCIO: Validations
        validateEventForCreation(event);
        
        // LÓGICA DE NEGOCIO: Set default values
        event.setActive(true);
        event.setCreatedAt(LocalDateTime.now());
        event.setUpdatedAt(LocalDateTime.now());
        
        // LÓGICA DE NEGOCIO: availableTickets = totalCapacity at creation
        if (event.getTotalCapacity() != null) {
            event.setAvailableTickets(event.getTotalCapacity());
        }
        
        return eventRepository.save(event);
    }
    
    // ==================== VALIDATIONS ====================
    
    private void validateEventForCreation(Event event) {
        if (event == null) {
            throw new InvalidEventDataException("event", "cant be null");
        }
        
        if (event.getName() == null || event.getName().trim().isEmpty()) {
            throw new InvalidEventDataException("name", "is mandatory");
        }
        
        if (event.getEventDate() == null) {
            throw new InvalidEventDataException("eventDate", "is mandatory");
        }
        
        if (event.getTicketPrice() == null || event.getTicketPrice().doubleValue() < 0) {
            throw new InvalidEventDataException("ticketPrice", "needs to be zero or positive");
        }
        
        if (event.getTotalCapacity() == null || event.getTotalCapacity() <= 0) {
            throw new InvalidEventDataException("totalCapacity", "must be greater than zero");
        }
        
        if (event.getVenueId() == null) {
            throw new InvalidEventDataException("venueId", "is mandatory");
        }
    }
}
