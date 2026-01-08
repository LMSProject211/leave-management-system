package com.company.payroll.dto;

import java.time.LocalDate;


public class AttendanceRequest {
    private Long employeeId;
    private String email;
    private LocalDate date;
    private String status;

    public AttendanceRequest() {
    }

    public AttendanceRequest(Long employeeId, LocalDate date, String status) {
        this.employeeId = employeeId;
        this.date = date;
        this.status = status;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

