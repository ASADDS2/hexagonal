package com.riwi.architecture.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Event {

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

    // Constructores

    public Event() {
    }

    public Event(Long id, String name, String description, LocalDateTime eventDate,
            LocalDateTime eventEndDate, String category, BigDecimal ticketPrice,
            Integer availableTickets, Integer totalCapacity, Boolean active,
            Long venueId, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.eventDate = eventDate;
        this.eventEndDate = eventEndDate;
        this.category = category;
        this.ticketPrice = ticketPrice;
        this.availableTickets = availableTickets;
        this.totalCapacity = totalCapacity;
        this.active = active;
        this.venueId = venueId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDateTime eventDate) {
        this.eventDate = eventDate;
    }

    public LocalDateTime getEventEndDate() {
        return eventEndDate;
    }

    public void setEventEndDate(LocalDateTime eventEndDate) {
        this.eventEndDate = eventEndDate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public BigDecimal getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(BigDecimal ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public Integer getAvailableTickets() {
        return availableTickets;
    }

    public void setAvailableTickets(Integer availableTickets) {
        this.availableTickets = availableTickets;
    }

    public Integer getTotalCapacity() {
        return totalCapacity;
    }

    public void setTotalCapacity(Integer totalCapacity) {
        this.totalCapacity = totalCapacity;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Long getVenueId() {
        return venueId;
    }

    public void setVenueId(Long venueId) {
        this.venueId = venueId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}