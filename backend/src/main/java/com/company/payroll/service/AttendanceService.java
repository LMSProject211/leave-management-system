package com.company.payroll.service;

import com.company.payroll.dto.ApiResponse;
import com.company.payroll.dto.AttendanceRequest;
import com.company.payroll.dto.AttendanceResponse;
import com.company.payroll.model.Attendance;
import com.company.payroll.model.AttendanceStatus;
import com.company.payroll.model.Employee;
import com.company.payroll.repository.AttendanceRepository;
import com.company.payroll.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public AttendanceService(AttendanceRepository attendanceRepository,
                             EmployeeRepository employeeRepository) {
        this.attendanceRepository = attendanceRepository;
        this.employeeRepository = employeeRepository;
    }

    
    private AttendanceResponse convertToResponse(Attendance attendance) {
        String employeeName = "Unknown";
        Optional<Employee> employeeOptional = employeeRepository.findById(attendance.getEmployeeId());
        if (employeeOptional.isPresent()) {
            employeeName = employeeOptional.get().getName();
        }

        return new AttendanceResponse(
                attendance.getId(),
                attendance.getEmployeeId(),
                employeeName,
                attendance.getDate(),
                attendance.getStatus().toString(),
                attendance.getCreatedAt(),
                attendance.getUpdatedAt()
        );
    }

    
    public ApiResponse<AttendanceResponse> markAttendance(AttendanceRequest request) {
        
        if (request.getDate() == null) {
            return new ApiResponse<>(false, "Date is required");
        }

        if (request.getStatus() == null || request.getStatus().trim().isEmpty()) {
            return new ApiResponse<>(false, "Status is required");
        }

        
        Optional<Employee> employeeOptional;
        if (request.getEmployeeId() != null) {
            employeeOptional = employeeRepository.findById(request.getEmployeeId());
        } else if (request.getEmail() != null && !request.getEmail().trim().isEmpty()) {
            employeeOptional = employeeRepository.findByEmail(request.getEmail().trim().toLowerCase());
        } else {
            return new ApiResponse<>(false, "Employee ID or email is required");
        }
        
        if (employeeOptional.isEmpty()) {
            return new ApiResponse<>(false, "Employee not found");
        }

        
        Employee employee = employeeOptional.get();
        AttendanceStatus status;
        try {
            status = AttendanceStatus.valueOf(request.getStatus().toUpperCase());
        } catch (IllegalArgumentException e) {
            return new ApiResponse<>(false, "Invalid status. Must be PRESENT or ABSENT");
        }

        
        Optional<Attendance> existingAttendance = attendanceRepository.findByEmployeeIdAndDate(
                employee.getId(), request.getDate());

        Attendance attendance;
        if (existingAttendance.isPresent()) {
            
            attendance = existingAttendance.get();
            attendance.setStatus(status);
        } else {
            
            attendance = new Attendance();
            attendance.setEmployeeId(employee.getId());
            attendance.setDate(request.getDate());
            attendance.setStatus(status);
        }

        Attendance savedAttendance = attendanceRepository.save(attendance);
        return new ApiResponse<>(true, "Attendance marked successfully", convertToResponse(savedAttendance));
    }

    
    public ApiResponse<List<AttendanceResponse>> getAllAttendance() {
        List<Attendance> attendances = attendanceRepository.findAll();
        List<AttendanceResponse> responses = attendances.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
        return new ApiResponse<>(true, "Attendance records retrieved successfully", responses);
    }

    
    public ApiResponse<List<AttendanceResponse>> getAttendanceByEmployee(Long employeeId) {
        
        Optional<Employee> employeeOptional = employeeRepository.findById(employeeId);
        if (employeeOptional.isEmpty()) {
            return new ApiResponse<>(false, "Employee not found");
        }

        List<Attendance> attendances = attendanceRepository.findByEmployeeId(employeeId);
        List<AttendanceResponse> responses = attendances.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
        return new ApiResponse<>(true, "Attendance records retrieved successfully", responses);
    }

    
    public ApiResponse<List<AttendanceResponse>> getAttendanceByEmployeeEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return new ApiResponse<>(false, "Email is required");
        }

        Optional<Employee> employeeOptional = employeeRepository.findByEmail(email.trim().toLowerCase());
        if (employeeOptional.isEmpty()) {
            return new ApiResponse<>(false, "Employee not found with email: " + email);
        }

        Employee employee = employeeOptional.get();
        List<Attendance> attendances = attendanceRepository.findByEmployeeId(employee.getId());
        List<AttendanceResponse> responses = attendances.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
        return new ApiResponse<>(true, "Attendance records retrieved successfully", responses);
    }
}

