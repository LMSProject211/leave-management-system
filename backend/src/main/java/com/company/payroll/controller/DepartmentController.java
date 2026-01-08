package com.company.payroll.controller;

import com.company.payroll.dto.ApiResponse;
import com.company.payroll.dto.DepartmentRequest;
import com.company.payroll.dto.DepartmentResponse;
import com.company.payroll.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/departments")
public class DepartmentController extends BaseController {

    private final DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    
    @PostMapping
    public ResponseEntity<ApiResponse<DepartmentResponse>> addDepartment(@RequestBody DepartmentRequest request) {
        ApiResponse<DepartmentResponse> response = departmentService.addDepartment(request);

        if (response.isSuccess()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    
    @GetMapping
    public ResponseEntity<ApiResponse<List<DepartmentResponse>>> getAllDepartments() {
        ApiResponse<List<DepartmentResponse>> response = departmentService.getAllDepartments();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<DepartmentResponse>> updateDepartment(
            @PathVariable Long id,
            @RequestBody DepartmentRequest request) {
        ApiResponse<DepartmentResponse> response = departmentService.updateDepartment(id, request);

        if (response.isSuccess()) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteDepartment(@PathVariable Long id) {
        ApiResponse<Void> response = departmentService.deleteDepartment(id);

        if (response.isSuccess()) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}

