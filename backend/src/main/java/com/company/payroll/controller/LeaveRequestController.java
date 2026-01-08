package com.company.payroll.controller;

import com.company.payroll.dto.ApiResponse;
import com.company.payroll.dto.LeaveRequestDto;
import com.company.payroll.dto.LeaveRequestResponse;
import com.company.payroll.service.LeaveRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/leaves")
public class LeaveRequestController extends BaseController {

    private final LeaveRequestService leaveRequestService;

    @Autowired
    public LeaveRequestController(LeaveRequestService leaveRequestService) {
        this.leaveRequestService = leaveRequestService;
    }

    
    @PostMapping
    public ResponseEntity<ApiResponse<LeaveRequestResponse>> applyLeave(@RequestBody LeaveRequestDto request) {
        ApiResponse<LeaveRequestResponse> response = leaveRequestService.applyLeave(request);

        if (response.isSuccess()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    
    @GetMapping
    public ResponseEntity<ApiResponse<List<LeaveRequestResponse>>> getAllLeaveRequests() {
        ApiResponse<List<LeaveRequestResponse>> response = leaveRequestService.getAllLeaveRequests();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<ApiResponse<List<LeaveRequestResponse>>> getLeaveRequestsByEmployee(
            @PathVariable Long employeeId) {
        ApiResponse<List<LeaveRequestResponse>> response = leaveRequestService.getLeaveRequestsByEmployee(employeeId);

        if (response.isSuccess()) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    
    @GetMapping("/employee/email/{email}")
    public ResponseEntity<ApiResponse<List<LeaveRequestResponse>>> getLeaveRequestsByEmployeeEmail(
            @PathVariable String email) {
        ApiResponse<List<LeaveRequestResponse>> response = leaveRequestService.getLeaveRequestsByEmployeeEmail(email);

        if (response.isSuccess()) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    
    @PutMapping("/{id}/approve")
    public ResponseEntity<ApiResponse<LeaveRequestResponse>> approveLeave(@PathVariable Long id) {
        ApiResponse<LeaveRequestResponse> response = leaveRequestService.approveLeave(id);

        if (response.isSuccess()) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    
    @PutMapping("/{id}/reject")
    public ResponseEntity<ApiResponse<LeaveRequestResponse>> rejectLeave(@PathVariable Long id) {
        ApiResponse<LeaveRequestResponse> response = leaveRequestService.rejectLeave(id);

        if (response.isSuccess()) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}


