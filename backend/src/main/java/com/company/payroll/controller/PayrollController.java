package com.company.payroll.controller;

import com.company.payroll.dto.ApiResponse;
import com.company.payroll.dto.PayrollRequest;
import com.company.payroll.dto.PayrollResponse;
import com.company.payroll.service.PayrollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/payroll")
public class PayrollController extends BaseController {

    private final PayrollService payrollService;

    @Autowired
    public PayrollController(PayrollService payrollService) {
        this.payrollService = payrollService;
    }

    
    @PostMapping
    public ResponseEntity<ApiResponse<PayrollResponse>> generatePayroll(@RequestBody PayrollRequest request) {
        ApiResponse<PayrollResponse> response = payrollService.generatePayroll(request);

        if (response.isSuccess()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<ApiResponse<List<PayrollResponse>>> getPayrollsByEmployee(
            @PathVariable Long employeeId) {
        ApiResponse<List<PayrollResponse>> response = payrollService.getPayrollsByEmployee(employeeId);

        if (response.isSuccess()) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    
    @GetMapping("/month/{month}")
    public ResponseEntity<ApiResponse<List<PayrollResponse>>> getPayrollsByMonth(
            @PathVariable String month) {
        ApiResponse<List<PayrollResponse>> response = payrollService.getPayrollsByMonth(month);

        if (response.isSuccess()) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}


