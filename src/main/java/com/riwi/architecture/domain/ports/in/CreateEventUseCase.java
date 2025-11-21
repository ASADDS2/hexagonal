package com.riwi.architecture.domain.ports.in;

import com.riwi.architecture.domain.model.Event;

public interface CreateEventUseCase {
    Event createEvent(Event event);
}
