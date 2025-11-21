package com.riwi.architecture.domain.ports.in;

import com.riwi.architecture.domain.model.Event;

public interface UpdateEventUseCase {
    Event updateEvent(Long id, Event event);
    Event sellTickets(Long eventId, Integer quantity);
    Event refundTickets(Long eventId, Integer quantity);
}