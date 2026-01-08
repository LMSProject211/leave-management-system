package com.company.payroll.model;

import jakarta.persistence.*;
import java.time.LocalDate;


@Entity
@Table(name = "holidays")
public class Holiday extends BaseEntity {

    @Column(name = "holiday_name", nullable = false, length = 255)
    private String holidayName;

    @Column(name = "holiday_date", nullable = false, unique = true)
    private LocalDate holidayDate;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    public Holiday() {
    }

    public Holiday(String holidayName, LocalDate holidayDate, String description) {
        this.holidayName = holidayName;
        this.holidayDate = holidayDate;
        this.description = description;
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
}

