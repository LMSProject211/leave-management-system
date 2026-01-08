package com.company.payroll.service;

import com.company.payroll.dto.ApiResponse;
import com.company.payroll.dto.DepartmentRequest;
import com.company.payroll.dto.DepartmentResponse;
import com.company.payroll.model.Department;
import com.company.payroll.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    
    private DepartmentResponse convertToResponse(Department department) {
        return new DepartmentResponse(
                department.getId(),
                department.getDepartmentName(),
                department.getDescription(),
                department.getCreatedAt(),
                department.getUpdatedAt()
        );
    }

    
    public ApiResponse<DepartmentResponse> addDepartment(DepartmentRequest request) {
        
        if (request.getDepartmentName() == null || request.getDepartmentName().trim().isEmpty()) {
            return new ApiResponse<>(false, "Department name is required");
        }

        
        if (departmentRepository.existsByDepartmentName(request.getDepartmentName().trim())) {
            return new ApiResponse<>(false, "Department already exists with this name");
        }

        
        Department department = new Department();
        department.setDepartmentName(request.getDepartmentName().trim());
        department.setDescription(request.getDescription() != null ? request.getDescription().trim() : null);

        Department savedDepartment = departmentRepository.save(department);
        return new ApiResponse<>(true, "Department added successfully", convertToResponse(savedDepartment));
    }

    
    public ApiResponse<List<DepartmentResponse>> getAllDepartments() {
        List<Department> departments = departmentRepository.findAll();
        List<DepartmentResponse> responses = departments.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
        return new ApiResponse<>(true, "Departments retrieved successfully", responses);
    }

    
    public ApiResponse<DepartmentResponse> updateDepartment(Long id, DepartmentRequest request) {
        
        if (id == null) {
            return new ApiResponse<>(false, "Department ID is required");
        }

        if (request.getDepartmentName() == null || request.getDepartmentName().trim().isEmpty()) {
            return new ApiResponse<>(false, "Department name is required");
        }

        
        Optional<Department> departmentOptional = departmentRepository.findById(id);
        if (departmentOptional.isEmpty()) {
            return new ApiResponse<>(false, "Department not found");
        }

        Department department = departmentOptional.get();

        
        String newName = request.getDepartmentName().trim();
        if (!department.getDepartmentName().equals(newName) && 
            departmentRepository.existsByDepartmentName(newName)) {
            return new ApiResponse<>(false, "Department already exists with this name");
        }

        
        department.setDepartmentName(newName);
        department.setDescription(request.getDescription() != null ? request.getDescription().trim() : null);

        Department updatedDepartment = departmentRepository.save(department);
        return new ApiResponse<>(true, "Department updated successfully", convertToResponse(updatedDepartment));
    }

    
    public ApiResponse<Void> deleteDepartment(Long id) {
        
        if (id == null) {
            return new ApiResponse<>(false, "Department ID is required");
        }

        
        Optional<Department> departmentOptional = departmentRepository.findById(id);
        if (departmentOptional.isEmpty()) {
            return new ApiResponse<>(false, "Department not found");
        }

        
        departmentRepository.deleteById(id);
        return new ApiResponse<>(true, "Department deleted successfully");
    }
}

