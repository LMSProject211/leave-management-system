package com.company.payroll.dto;

import java.time.LocalDateTime;


public class DepartmentResponse {
    private Long id;
    private String departmentName;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public DepartmentResponse() {
    }

    public DepartmentResponse(Long id, String departmentName, String description,
                              LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.departmentName = departmentName;
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

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
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

