package com.company.payroll.dto;

import java.math.BigDecimal;


public class EmployeeRequest {
    private String name;
    private String email;
    private String department;
    private String designation;
    private BigDecimal basicSalary;
    private String status;

    public EmployeeRequest() {
    }

    public EmployeeRequest(String name, String email, String department, String designation, BigDecimal basicSalary, String status) {
        this.name = name;
        this.email = email;
        this.department = department;
        this.designation = designation;
        this.basicSalary = basicSalary;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public BigDecimal getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(BigDecimal basicSalary) {
        this.basicSalary = basicSalary;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}


