package com.riwi.architecture.application.usecase;

import com.riwi.architecture.domain.ports.in.DeleteVenueUseCase;
import com.riwi.architecture.domain.ports.out.VenueRepository;
import com.riwi.architecture.domain.exceptions.InvalidVenueDataException;
import com.riwi.architecture.domain.exceptions.VenueNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementación del caso de uso: Eliminar Venue
 * CAPA DE APLICACIÓN - Contiene lógica de negocio
 */
@Service
@Transactional
public class DeleteVenueUseCaseImpl implements DeleteVenueUseCase {
    
    private final VenueRepository venueRepository;

    public DeleteVenueUseCaseImpl(VenueRepository venueRepository) {
        this.venueRepository = venueRepository;
    }

    @Override
    public void deleteVenue(Long id) {
        validateId(id);
        
        if (!venueRepository.existsById(id)) {
            throw new VenueNotFoundException(id);
        }
        
        // TODO: LÓGICA DE NEGOCIO adicional
        // Verificar que no haya eventos activos asociados a este venue
        // antes de eliminarlo
        
        venueRepository.deleteById(id);
    }
    
    // ==================== VALIDACIONES ====================
    
    private void validateId(Long id) {
        if (id == null || id <= 0) {
            throw new InvalidVenueDataException("id", "debe ser un valor positivo");
        }
    }
}