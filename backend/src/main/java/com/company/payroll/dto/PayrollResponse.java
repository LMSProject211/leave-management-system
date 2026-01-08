package com.company.payroll.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;


public class PayrollResponse {
    private Long id;
    private Long employeeId;
    private String employeeName;
    private String month;
    private BigDecimal basicSalary;
    private BigDecimal taxAmount;
    private BigDecimal leaveDeduction;
    private BigDecimal netSalary;
    private LocalDate generatedDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public PayrollResponse() {
    }

    public PayrollResponse(Long id, Long employeeId, String employeeName, String month,
                          BigDecimal basicSalary, BigDecimal taxAmount, BigDecimal leaveDeduction,
                          BigDecimal netSalary, LocalDate generatedDate,
                          LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.month = month;
        this.basicSalary = basicSalary;
        this.taxAmount = taxAmount;
        this.leaveDeduction = leaveDeduction;
        this.netSalary = netSalary;
        this.generatedDate = generatedDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public BigDecimal getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(BigDecimal basicSalary) {
        this.basicSalary = basicSalary;
    }

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }

    public BigDecimal getLeaveDeduction() {
        return leaveDeduction;
    }

    public void setLeaveDeduction(BigDecimal leaveDeduction) {
        this.leaveDeduction = leaveDeduction;
    }

    public BigDecimal getNetSalary() {
        return netSalary;
    }

    public void setNetSalary(BigDecimal netSalary) {
        this.netSalary = netSalary;
    }

    public LocalDate getGeneratedDate() {
        return generatedDate;
    }

    public void setGeneratedDate(LocalDate generatedDate) {
        this.generatedDate = generatedDate;
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


