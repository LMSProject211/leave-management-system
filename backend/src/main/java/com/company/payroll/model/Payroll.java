package com.company.payroll.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;


@Entity
@Table(name = "payrolls")
public class Payroll extends BaseEntity {

    @Column(name = "employee_id", nullable = false)
    private Long employeeId;

    @Column(name = "month", nullable = false, length = 7)
    private String month; 

    @Column(name = "basic_salary", nullable = false, precision = 10, scale = 2)
    private BigDecimal basicSalary;

    @Column(name = "tax_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal taxAmount;

    @Column(name = "leave_deduction", nullable = false, precision = 10, scale = 2)
    private BigDecimal leaveDeduction;

    @Column(name = "net_salary", nullable = false, precision = 10, scale = 2)
    private BigDecimal netSalary;

    @Column(name = "generated_date", nullable = false)
    private LocalDate generatedDate;

    public Payroll() {
    }

    public Payroll(Long employeeId, String month, BigDecimal basicSalary, BigDecimal taxAmount,
                    BigDecimal leaveDeduction, BigDecimal netSalary) {
        this.employeeId = employeeId;
        this.month = month;
        this.basicSalary = basicSalary;
        this.taxAmount = taxAmount;
        this.leaveDeduction = leaveDeduction;
        this.netSalary = netSalary;
        this.generatedDate = LocalDate.now();
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
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
}

