package com.riwi.architecture.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventEntity {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime eventDate;
    private LocalDateTime eventEndDate;
    private String category;
    private BigDecimal ticketPrice;
    private Integer availableTickets;
    private Integer totalCapacity;
    private Boolean active;
    private Long venueId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Verify tickets availability
    public boolean hasAvailableTickets() {
        return active != null && active &&
                availableTickets != null && availableTickets > 0;
    }

    // Verify if the event passed
    public boolean isPastEvent() {
        if (eventEndDate != null){
            return LocalDateTime.now().isAfter(eventEndDate);
        }
        return eventDate != null && LocalDateTime.now().isAfter(eventDate);
    }

    // Sell tickets
    public void sellTickets(Integer quantity) {
        if (quantity == null || quantity <= 0){
            throw new IllegalArgumentException("Quantity needs to be more than 0");
        }

        
    }
}
