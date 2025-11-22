package com.riwi.architecture.application.usecase;

import com.riwi.architecture.domain.ports.in.DeleteEventUseCase;
import com.riwi.architecture.domain.ports.out.EventRepository;
import com.riwi.architecture.domain.exceptions.EventNotFoundException;
import com.riwi.architecture.domain.exceptions.InvalidEventDataException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementación del caso de uso: Eliminar Event
 * CAPA DE APLICACIÓN - Contiene lógica de negocio
 */
@Service
@Transactional
public class DeleteEventUseCaseImpl implements DeleteEventUseCase {
    
    private final EventRepository eventRepository;

    public DeleteEventUseCaseImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public void deleteEvent(Long id) {
        validateId(id);
        
        if (!eventRepository.existsById(id)) {
            throw new EventNotFoundException(id);
        }
        
        eventRepository.deleteById(id);
    }
    
    // ==================== VALIDACIONES ====================
    
    private void validateId(Long id) {
        if (id == null || id <= 0) {
            throw new InvalidEventDataException("id", "must be a positive value");
        }
    }
}
