package com.company.payroll.repository;

import com.company.payroll.model.Employee;
import com.company.payroll.model.EmployeeStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    
    Optional<Employee> findByEmail(String email);

    
    boolean existsByEmail(String email);

    
    List<Employee> findByStatus(EmployeeStatus status);

    
    List<Employee> findByDepartment(String department);
}


