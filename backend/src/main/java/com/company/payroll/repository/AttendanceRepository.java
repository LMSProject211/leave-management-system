package com.company.payroll.repository;

import com.company.payroll.model.Attendance;
import com.company.payroll.model.AttendanceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    
    List<Attendance> findByEmployeeId(Long employeeId);

    
    Optional<Attendance> findByEmployeeIdAndDate(Long employeeId, LocalDate date);

    
    List<Attendance> findByDate(LocalDate date);

    
    List<Attendance> findByStatus(AttendanceStatus status);

    
    boolean existsByEmployeeIdAndDate(Long employeeId, LocalDate date);
}

