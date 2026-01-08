package com.company.payroll.service;

import com.company.payroll.dto.ApiResponse;
import com.company.payroll.dto.PayrollRequest;
import com.company.payroll.dto.PayrollResponse;
import com.company.payroll.model.Employee;
import com.company.payroll.model.LeaveRequest;
import com.company.payroll.model.LeaveStatus;
import com.company.payroll.model.Payroll;
import com.company.payroll.repository.EmployeeRepository;
import com.company.payroll.repository.LeaveRequestRepository;
import com.company.payroll.repository.PayrollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class PayrollService {

    private static final BigDecimal TAX_RATE = new BigDecimal("0.10"); 
    private static final BigDecimal DEFAULT_LEAVE_DEDUCTION_PER_DAY = new BigDecimal("100.00"); 

    private final PayrollRepository payrollRepository;
    private final EmployeeRepository employeeRepository;
    private final LeaveRequestRepository leaveRequestRepository;

    @Autowired
    public PayrollService(PayrollRepository payrollRepository,
                         EmployeeRepository employeeRepository,
                         LeaveRequestRepository leaveRequestRepository) {
        this.payrollRepository = payrollRepository;
        this.employeeRepository = employeeRepository;
        this.leaveRequestRepository = leaveRequestRepository;
    }

    
    private PayrollResponse convertToResponse(Payroll payroll) {
        String employeeName = "Unknown";
        Optional<Employee> employeeOptional = employeeRepository.findById(payroll.getEmployeeId());
        if (employeeOptional.isPresent()) {
            employeeName = employeeOptional.get().getName();
        }

        return new PayrollResponse(
                payroll.getId(),
                payroll.getEmployeeId(),
                employeeName,
                payroll.getMonth(),
                payroll.getBasicSalary(),
                payroll.getTaxAmount(),
                payroll.getLeaveDeduction(),
                payroll.getNetSalary(),
                payroll.getGeneratedDate(),
                payroll.getCreatedAt(),
                payroll.getUpdatedAt()
        );
    }

    
    private int calculateLeaveDays(Long employeeId, String month) {
        List<LeaveRequest> approvedLeaves = leaveRequestRepository.findByEmployeeIdAndStatus(
                employeeId, LeaveStatus.APPROVED);

        int totalDays = 0;
        YearMonth targetMonth = YearMonth.parse(month);

        for (LeaveRequest leave : approvedLeaves) {
            LocalDate startDate = leave.getStartDate();
            LocalDate endDate = leave.getEndDate();

            
            YearMonth leaveStartMonth = YearMonth.from(startDate);
            YearMonth leaveEndMonth = YearMonth.from(endDate);

            if (leaveStartMonth.isBefore(targetMonth) || leaveStartMonth.equals(targetMonth)) {
                if (leaveEndMonth.isAfter(targetMonth) || leaveEndMonth.equals(targetMonth)) {
                    
                    LocalDate monthStart = targetMonth.atDay(1);
                    LocalDate monthEnd = targetMonth.atEndOfMonth();

                    LocalDate effectiveStart = startDate.isAfter(monthStart) ? startDate : monthStart;
                    LocalDate effectiveEnd = endDate.isBefore(monthEnd) ? endDate : monthEnd;

                    long days = java.time.temporal.ChronoUnit.DAYS.between(effectiveStart, effectiveEnd) + 1;
                    totalDays += (int) days;
                }
            }
        }

        return totalDays;
    }

    
    public ApiResponse<PayrollResponse> generatePayroll(PayrollRequest request) {
        
        if (request.getEmployeeId() == null) {
            return new ApiResponse<>(false, "Employee ID is required");
        }

        if (request.getMonth() == null || request.getMonth().trim().isEmpty()) {
            return new ApiResponse<>(false, "Month is required (format: YYYY-MM)");
        }

        
        try {
            YearMonth.parse(request.getMonth());
        } catch (DateTimeParseException e) {
            return new ApiResponse<>(false, "Invalid month format. Use YYYY-MM (e.g., 2024-01)");
        }

        
        Optional<Employee> employeeOptional = employeeRepository.findById(request.getEmployeeId());
        if (employeeOptional.isEmpty()) {
            return new ApiResponse<>(false, "Employee not found");
        }

        Employee employee = employeeOptional.get();

        
        Optional<Payroll> existingPayroll = payrollRepository.findByEmployeeIdAndMonth(
                request.getEmployeeId(), request.getMonth());
        if (existingPayroll.isPresent()) {
            return new ApiResponse<>(false, "Payroll already exists for this employee and month");
        }

        
        BigDecimal basicSalary = employee.getBasicSalary();

        
        BigDecimal taxAmount = basicSalary.multiply(TAX_RATE).setScale(2, RoundingMode.HALF_UP);

        
        int leaveDays = calculateLeaveDays(request.getEmployeeId(), request.getMonth());
        BigDecimal leaveDeductionPerDay = request.getLeaveDeductionPerDay() != null
                ? request.getLeaveDeductionPerDay()
                : DEFAULT_LEAVE_DEDUCTION_PER_DAY;
        BigDecimal leaveDeduction = leaveDeductionPerDay
                .multiply(new BigDecimal(leaveDays))
                .setScale(2, RoundingMode.HALF_UP);

        
        BigDecimal netSalary = basicSalary
                .subtract(taxAmount)
                .subtract(leaveDeduction)
                .setScale(2, RoundingMode.HALF_UP);

        
        Payroll payroll = new Payroll();
        payroll.setEmployeeId(request.getEmployeeId());
        payroll.setMonth(request.getMonth());
        payroll.setBasicSalary(basicSalary);
        payroll.setTaxAmount(taxAmount);
        payroll.setLeaveDeduction(leaveDeduction);
        payroll.setNetSalary(netSalary);
        payroll.setGeneratedDate(LocalDate.now());

        Payroll savedPayroll = payrollRepository.save(payroll);
        return new ApiResponse<>(true, "Payroll generated successfully", convertToResponse(savedPayroll));
    }

    
    public ApiResponse<List<PayrollResponse>> getPayrollsByEmployee(Long employeeId) {
        
        Optional<Employee> employeeOptional = employeeRepository.findById(employeeId);
        if (employeeOptional.isEmpty()) {
            return new ApiResponse<>(false, "Employee not found");
        }

        List<Payroll> payrolls = payrollRepository.findByEmployeeId(employeeId);
        List<PayrollResponse> responses = payrolls.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
        return new ApiResponse<>(true, "Payrolls retrieved successfully", responses);
    }

    
    public ApiResponse<List<PayrollResponse>> getPayrollsByMonth(String month) {
        
        try {
            YearMonth.parse(month);
        } catch (DateTimeParseException e) {
            return new ApiResponse<>(false, "Invalid month format. Use YYYY-MM (e.g., 2024-01)");
        }

        List<Payroll> payrolls = payrollRepository.findByMonth(month);
        List<PayrollResponse> responses = payrolls.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
        return new ApiResponse<>(true, "Payrolls retrieved successfully", responses);
    }
}

