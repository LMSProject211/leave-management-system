package com.company.payroll.controller;

import com.company.payroll.dto.ApiResponse;
import com.company.payroll.dto.AttendanceRequest;
import com.company.payroll.dto.AttendanceResponse;
import com.company.payroll.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/attendance")
public class AttendanceController extends BaseController {

    private final AttendanceService attendanceService;

    @Autowired
    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    
    @PostMapping
    public ResponseEntity<ApiResponse<AttendanceResponse>> markAttendance(@RequestBody AttendanceRequest request) {
        ApiResponse<AttendanceResponse> response = attendanceService.markAttendance(request);

        if (response.isSuccess()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    
    @GetMapping
    public ResponseEntity<ApiResponse<List<AttendanceResponse>>> getAllAttendance() {
        ApiResponse<List<AttendanceResponse>> response = attendanceService.getAllAttendance();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<ApiResponse<List<AttendanceResponse>>> getAttendanceByEmployee(
            @PathVariable Long employeeId) {
        ApiResponse<List<AttendanceResponse>> response = attendanceService.getAttendanceByEmployee(employeeId);

        if (response.isSuccess()) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    
    @GetMapping("/employee/email/{email}")
    public ResponseEntity<ApiResponse<List<AttendanceResponse>>> getAttendanceByEmployeeEmail(
            @PathVariable String email) {
        ApiResponse<List<AttendanceResponse>> response = attendanceService.getAttendanceByEmployeeEmail(email);

        if (response.isSuccess()) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}

