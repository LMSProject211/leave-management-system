package com.company.payroll.repository;

import com.company.payroll.model.LeaveRequest;
import com.company.payroll.model.LeaveStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {

    
    List<LeaveRequest> findByEmployeeId(Long employeeId);

    
    List<LeaveRequest> findByStatus(LeaveStatus status);

    
    List<LeaveRequest> findByEmployeeIdAndStatus(Long employeeId, LeaveStatus status);
}


