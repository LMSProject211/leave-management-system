package in.leavemanagementsystem.leavemanagement.repository;


import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import in.leavemanagementsystem.leavemanagement.entity.LeaveRequest;
import in.leavemanagementsystem.leavemanagement.entity.User;
import in.leavemanagementsystem.leavemanagement.enums.LeaveStatus;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {

    // Employee: view own leave requests
    List<LeaveRequest> findByUser(User user);

    // Manager: view pending requests
    List<LeaveRequest> findByStatus(LeaveStatus status);

    // Validation: detect overlapping leaves
    List<LeaveRequest> findByUserAndStatusInAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
            User user,
            List<LeaveStatus> statuses,
            LocalDate endDate,
            LocalDate startDate
    );
}
