package com.riwi.architecture.domain.ports.in;

public class UpdateEventUsecase {
    Event updateEvent(Long Id, Event event);
    Event sellTickets(Long eventId, Integer quantity);
    Event refundTickets(Long eventId, Integer quantity);
}
