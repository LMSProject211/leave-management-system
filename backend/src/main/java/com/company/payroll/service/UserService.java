package com.company.payroll.service;

import com.company.payroll.dto.AuthResponse;
import com.company.payroll.dto.LoginRequest;
import com.company.payroll.dto.RegisterRequest;
import com.company.payroll.model.Employee;
import com.company.payroll.model.User;
import com.company.payroll.model.UserRole;
import com.company.payroll.repository.EmployeeRepository;
import com.company.payroll.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public UserService(UserRepository userRepository, EmployeeRepository employeeRepository) {
        this.userRepository = userRepository;
        this.employeeRepository = employeeRepository;
    }

    
    public AuthResponse register(RegisterRequest request) {
        
        if (request.getName() == null || request.getName().trim().isEmpty()) {
            return new AuthResponse(false, "Name is required");
        }

        if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
            return new AuthResponse(false, "Email is required");
        }

        if (request.getPassword() == null || request.getPassword().trim().isEmpty()) {
            return new AuthResponse(false, "Password is required");
        }

        
        if (userRepository.existsByEmail(request.getEmail())) {
            return new AuthResponse(false, "Email already registered");
        }

        
        UserRole userRole;
        if (request.getRole() == null || request.getRole().trim().isEmpty()) {
            
            userRole = UserRole.EMPLOYEE;
        } else {
            try {
                userRole = UserRole.valueOf(request.getRole().toUpperCase());
            } catch (IllegalArgumentException e) {
                return new AuthResponse(false, "Invalid role. Must be ADMIN or EMPLOYEE");
            }
        }

        
        User user = new User();
        user.setName(request.getName().trim());
        user.setEmail(request.getEmail().trim().toLowerCase());
        user.setPassword(request.getPassword()); 
        user.setRole(userRole);

        
        User savedUser = userRepository.save(user);

        
        if (userRole == UserRole.EMPLOYEE) {
            if (!employeeRepository.existsByEmail(savedUser.getEmail())) {
                Employee employee = new Employee();
                employee.setName(savedUser.getName());
                employee.setEmail(savedUser.getEmail());
                employee.setDepartment("General");
                employee.setDesignation("Employee");
                employee.setBasicSalary(new BigDecimal("0.00"));
                employeeRepository.save(employee);
            }
        }

        
        AuthResponse.UserData userData = new AuthResponse.UserData(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail(),
                savedUser.getRole().toString()
        );

        return new AuthResponse(true, "User registered successfully", userData);
    }

    
    public AuthResponse login(LoginRequest request) {
        
        if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
            return new AuthResponse(false, "Email is required");
        }

        if (request.getPassword() == null || request.getPassword().trim().isEmpty()) {
            return new AuthResponse(false, "Password is required");
        }

        
        Optional<User> userOptional = userRepository.findByEmail(request.getEmail().trim().toLowerCase());

        if (userOptional.isEmpty()) {
            return new AuthResponse(false, "Invalid email or password");
        }

        User user = userOptional.get();

        
        if (!user.getPassword().equals(request.getPassword())) {
            return new AuthResponse(false, "Invalid email or password");
        }

        
        if (user.getRole() == UserRole.EMPLOYEE && !employeeRepository.existsByEmail(user.getEmail())) {
            Employee employee = new Employee();
            employee.setName(user.getName());
            employee.setEmail(user.getEmail());
            employee.setDepartment("General");
            employee.setDesignation("Employee");
            employee.setBasicSalary(new BigDecimal("0.00"));
            employeeRepository.save(employee);
        }

        
        AuthResponse.UserData userData = new AuthResponse.UserData(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole().toString()
        );

        return new AuthResponse(true, "Login successful", userData);
    }
}


