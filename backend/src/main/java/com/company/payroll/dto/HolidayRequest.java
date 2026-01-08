package com.company.payroll.dto;

import java.time.LocalDate;


public class HolidayRequest {
    private String holidayName;
    private LocalDate holidayDate;
    private String description;

    public HolidayRequest() {
    }

    public HolidayRequest(String holidayName, LocalDate holidayDate, String description) {
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

