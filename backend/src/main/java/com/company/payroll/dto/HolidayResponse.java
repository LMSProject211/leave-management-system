package com.company.payroll.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;


public class HolidayResponse {
    private Long id;
    private String holidayName;
    private LocalDate holidayDate;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public HolidayResponse() {
    }

    public HolidayResponse(Long id, String holidayName, LocalDate holidayDate, String description,
                          LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.holidayName = holidayName;
        this.holidayDate = holidayDate;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHolidayName() {
        return holidayName;
    }

    public void setHolidayName(String holidayName) {
        this.holidayName = holidayName;
    }

    public LocalDate getHolidayDate() {
        return holidayDate;
    }

    public void setHolidayDate(LocalDate holidayDate) {
        this.holidayDate = holidayDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

