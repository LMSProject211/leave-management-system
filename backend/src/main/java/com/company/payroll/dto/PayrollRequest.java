package com.company.payroll.dto;

import java.math.BigDecimal;


public class PayrollRequest {
    private Long employeeId;
    private String month; 
    private BigDecimal leaveDeductionPerDay; 

    public PayrollRequest() {
    }

    public PayrollRequest(Long employeeId, String month, BigDecimal leaveDeductionPerDay) {
        this.employeeId = employeeId;
        this.month = month;
        this.leaveDeductionPerDay = leaveDeductionPerDay;
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

    public BigDecimal getLeaveDeductionPerDay() {
        return leaveDeductionPerDay;
    }

    public void setLeaveDeductionPerDay(BigDecimal leaveDeductionPerDay) {
        this.leaveDeductionPerDay = leaveDeductionPerDay;
    }
}


