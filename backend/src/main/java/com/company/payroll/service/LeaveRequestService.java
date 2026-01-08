package com.company.payroll.service;

import com.company.payroll.dto.ApiResponse;
import com.company.payroll.dto.LeaveRequestDto;
import com.company.payroll.dto.LeaveRequestResponse;
import com.company.payroll.model.Employee;
import com.company.payroll.model.LeaveRequest;
import com.company.payroll.model.LeaveStatus;
import com.company.payroll.model.LeaveType;
import com.company.payroll.repository.EmployeeRepository;
import com.company.payroll.repository.LeaveRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class LeaveRequestService {

    private final LeaveRequestRepository leaveRequestRepository;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public LeaveRequestService(LeaveRequestRepository leaveRequestRepository,
                               EmployeeRepository employeeRepository) {
        this.leaveRequestRepository = leaveRequestRepository;
        this.employeeRepository = employeeRepository;
    }

    
    private LeaveRequestResponse convertToResponse(LeaveRequest leaveRequest) {
        String employeeName = "Unknown";
        Optional<Employee> employeeOptional = employeeRepository.findById(leaveRequest.getEmployeeId());
        if (employeeOptional.isPresent()) {
            employeeName = employeeOptional.get().getName();
        }

        return new LeaveRequestResponse(
                leaveRequest.getId(),
                leaveRequest.getEmployeeId(),
                employeeName,
                leaveRequest.getLeaveType().toString(),
                leaveRequest.getStartDate(),
                leaveRequest.getEndDate(),
                leaveRequest.getReason(),
                leaveRequest.getStatus().toString(),
                leaveRequest.getCreatedAt(),
                leaveRequest.getUpdatedAt()
        );
    }

    
    public ApiResponse<LeaveRequestResponse> applyLeave(LeaveRequestDto request) {
        
        if (request.getLeaveType() == null || request.getLeaveType().trim().isEmpty()) {
            return new ApiResponse<>(false, "Leave type is required");
        }

        if (request.getStartDate() == null) {
            return new ApiResponse<>(false, "Start date is required");
        }

        if (request.getEndDate() == null) {
            return new ApiResponse<>(false, "End date is required");
        }

        if (request.getEndDate().isBefore(request.getStartDate())) {
            return new ApiResponse<>(false, "End date must be after or equal to start date");
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

        
        LeaveType leaveType;
        try {
            leaveType = LeaveType.valueOf(request.getLeaveType().toUpperCase());
        } catch (IllegalArgumentException e) {
            return new ApiResponse<>(false, "Invalid leave type. Must be CASUAL, SICK, or PAID");
        }

        
        Employee employee = employeeOptional.get();
        LeaveRequest leaveRequest = new LeaveRequest();
        leaveRequest.setEmployeeId(employee.getId());
        leaveRequest.setLeaveType(leaveType);
        leaveRequest.setStartDate(request.getStartDate());
        leaveRequest.setEndDate(request.getEndDate());
        leaveRequest.setReason(request.getReason() != null ? request.getReason().trim() : null);
        leaveRequest.setStatus(LeaveStatus.PENDING);

        LeaveRequest savedLeaveRequest = leaveRequestRepository.save(leaveRequest);
        return new ApiResponse<>(true, "Leave request submitted successfully", convertToResponse(savedLeaveRequest));
    }

    
    public ApiResponse<List<LeaveRequestResponse>> getAllLeaveRequests() {
        List<LeaveRequest> leaveRequests = leaveRequestRepository.findAll();
        List<LeaveRequestResponse> responses = leaveRequests.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
        return new ApiResponse<>(true, "Leave requests retrieved successfully", responses);
    }

    
    public ApiResponse<List<LeaveRequestResponse>> getLeaveRequestsByEmployee(Long employeeId) {
        
        Optional<Employee> employeeOptional = employeeRepository.findById(employeeId);
        if (employeeOptional.isEmpty()) {
            return new ApiResponse<>(false, "Employee not found");
        }

        List<LeaveRequest> leaveRequests = leaveRequestRepository.findByEmployeeId(employeeId);
        List<LeaveRequestResponse> responses = leaveRequests.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
        return new ApiResponse<>(true, "Leave requests retrieved successfully", responses);
    }

    
    public ApiResponse<List<LeaveRequestResponse>> getLeaveRequestsByEmployeeEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return new ApiResponse<>(false, "Email is required");
        }

        Optional<Employee> employeeOptional = employeeRepository.findByEmail(email.trim().toLowerCase());
        if (employeeOptional.isEmpty()) {
            return new ApiResponse<>(false, "Employee not found with email: " + email);
        }

        Employee employee = employeeOptional.get();
        List<LeaveRequest> leaveRequests = leaveRequestRepository.findByEmployeeId(employee.getId());
        List<LeaveRequestResponse> responses = leaveRequests.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
        return new ApiResponse<>(true, "Leave requests retrieved successfully", responses);
    }

    
    public ApiResponse<LeaveRequestResponse> approveLeave(Long id) {
        Optional<LeaveRequest> leaveRequestOptional = leaveRequestRepository.findById(id);

        if (leaveRequestOptional.isEmpty()) {
            return new ApiResponse<>(false, "Leave request not found");
        }

        LeaveRequest leaveRequest = leaveRequestOptional.get();
        leaveRequest.setStatus(LeaveStatus.APPROVED);
        LeaveRequest updatedLeaveRequest = leaveRequestRepository.save(leaveRequest);

        return new ApiResponse<>(true, "Leave request approved successfully", convertToResponse(updatedLeaveRequest));
    }

    
    public ApiResponse<LeaveRequestResponse> rejectLeave(Long id) {
        Optional<LeaveRequest> leaveRequestOptional = leaveRequestRepository.findById(id);

        if (leaveRequestOptional.isEmpty()) {
            return new ApiResponse<>(false, "Leave request not found");
        }

        LeaveRequest leaveRequest = leaveRequestOptional.get();
        leaveRequest.setStatus(LeaveStatus.REJECTED);
        LeaveRequest updatedLeaveRequest = leaveRequestRepository.save(leaveRequest);

        return new ApiResponse<>(true, "Leave request rejected successfully", convertToResponse(updatedLeaveRequest));
    }
}


