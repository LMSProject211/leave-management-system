package com.company.payroll.service;

import com.company.payroll.dto.ApiResponse;
import com.company.payroll.dto.EmployeeRequest;
import com.company.payroll.dto.EmployeeResponse;
import com.company.payroll.model.Employee;
import com.company.payroll.model.EmployeeStatus;
import com.company.payroll.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    
    private EmployeeResponse convertToResponse(Employee employee) {
        return new EmployeeResponse(
                employee.getId(),
                employee.getName(),
                employee.getEmail(),
                employee.getDepartment(),
                employee.getDesignation(),
                employee.getBasicSalary(),
                employee.getStatus().toString(),
                employee.getCreatedAt(),
                employee.getUpdatedAt()
        );
    }

    
    public ApiResponse<EmployeeResponse> createEmployee(EmployeeRequest request) {
        
        if (request.getName() == null || request.getName().trim().isEmpty()) {
            return new ApiResponse<>(false, "Name is required");
        }

        if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
            return new ApiResponse<>(false, "Email is required");
        }

        if (request.getDepartment() == null || request.getDepartment().trim().isEmpty()) {
            return new ApiResponse<>(false, "Department is required");
        }

        if (request.getDesignation() == null || request.getDesignation().trim().isEmpty()) {
            return new ApiResponse<>(false, "Designation is required");
        }

        if (request.getBasicSalary() == null || request.getBasicSalary().compareTo(java.math.BigDecimal.ZERO) < 0) {
            return new ApiResponse<>(false, "Basic salary is required and must be non-negative");
        }

        
        if (employeeRepository.existsByEmail(request.getEmail().trim().toLowerCase())) {
            return new ApiResponse<>(false, "Email already exists");
        }

        
        Employee employee = new Employee();
        employee.setName(request.getName().trim());
        employee.setEmail(request.getEmail().trim().toLowerCase());
        employee.setDepartment(request.getDepartment().trim());
        employee.setDesignation(request.getDesignation().trim());
        employee.setBasicSalary(request.getBasicSalary());

        
        if (request.getStatus() != null && !request.getStatus().trim().isEmpty()) {
            try {
                employee.setStatus(EmployeeStatus.valueOf(request.getStatus().toUpperCase()));
            } catch (IllegalArgumentException e) {
                return new ApiResponse<>(false, "Invalid status. Must be ACTIVE or INACTIVE");
            }
        }

        Employee savedEmployee = employeeRepository.save(employee);
        return new ApiResponse<>(true, "Employee created successfully", convertToResponse(savedEmployee));
    }

    
    public ApiResponse<List<EmployeeResponse>> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        List<EmployeeResponse> responses = employees.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
        return new ApiResponse<>(true, "Employees retrieved successfully", responses);
    }

    
    public ApiResponse<EmployeeResponse> getEmployeeById(Long id) {
        Optional<Employee> employeeOptional = employeeRepository.findById(id);

        if (employeeOptional.isEmpty()) {
            return new ApiResponse<>(false, "Employee not found with id: " + id);
        }

        return new ApiResponse<>(true, "Employee retrieved successfully", convertToResponse(employeeOptional.get()));
    }

    
    public ApiResponse<EmployeeResponse> getEmployeeByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return new ApiResponse<>(false, "Email is required");
        }

        Optional<Employee> employeeOptional = employeeRepository.findByEmail(email.trim().toLowerCase());

        if (employeeOptional.isEmpty()) {
            return new ApiResponse<>(false, "Employee not found with email: " + email);
        }

        return new ApiResponse<>(true, "Employee retrieved successfully", convertToResponse(employeeOptional.get()));
    }

    
    public ApiResponse<EmployeeResponse> updateEmployee(Long id, EmployeeRequest request) {
        Optional<Employee> employeeOptional = employeeRepository.findById(id);

        if (employeeOptional.isEmpty()) {
            return new ApiResponse<>(false, "Employee not found with id: " + id);
        }

        Employee employee = employeeOptional.get();

        
        if (request.getName() != null && !request.getName().trim().isEmpty()) {
            employee.setName(request.getName().trim());
        }

        if (request.getEmail() != null && !request.getEmail().trim().isEmpty()) {
            String newEmail = request.getEmail().trim().toLowerCase();
            
            if (!employee.getEmail().equals(newEmail) && employeeRepository.existsByEmail(newEmail)) {
                return new ApiResponse<>(false, "Email already exists");
            }
            employee.setEmail(newEmail);
        }

        if (request.getDepartment() != null && !request.getDepartment().trim().isEmpty()) {
            employee.setDepartment(request.getDepartment().trim());
        }

        if (request.getDesignation() != null && !request.getDesignation().trim().isEmpty()) {
            employee.setDesignation(request.getDesignation().trim());
        }

        if (request.getBasicSalary() != null && request.getBasicSalary().compareTo(java.math.BigDecimal.ZERO) >= 0) {
            employee.setBasicSalary(request.getBasicSalary());
        }

        if (request.getStatus() != null && !request.getStatus().trim().isEmpty()) {
            try {
                employee.setStatus(EmployeeStatus.valueOf(request.getStatus().toUpperCase()));
            } catch (IllegalArgumentException e) {
                return new ApiResponse<>(false, "Invalid status. Must be ACTIVE or INACTIVE");
            }
        }

        Employee updatedEmployee = employeeRepository.save(employee);
        return new ApiResponse<>(true, "Employee updated successfully", convertToResponse(updatedEmployee));
    }

    
    public ApiResponse<Void> deleteEmployee(Long id) {
        Optional<Employee> employeeOptional = employeeRepository.findById(id);

        if (employeeOptional.isEmpty()) {
            return new ApiResponse<>(false, "Employee not found with id: " + id);
        }

        Employee employee = employeeOptional.get();
        
        
        employee.setStatus(EmployeeStatus.INACTIVE);
        employeeRepository.save(employee);

        return new ApiResponse<>(true, "Employee deleted successfully (soft delete)");
    }
}


