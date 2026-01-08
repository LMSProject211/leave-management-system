package com.company.payroll.repository;

import com.company.payroll.model.Payroll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface PayrollRepository extends JpaRepository<Payroll, Long> {

    
    List<Payroll> findByEmployeeId(Long employeeId);

    
    List<Payroll> findByMonth(String month);

    
    Optional<Payroll> findByEmployeeIdAndMonth(Long employeeId, String month);
}


