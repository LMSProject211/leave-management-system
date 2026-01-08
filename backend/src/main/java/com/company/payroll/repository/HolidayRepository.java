package com.company.payroll.repository;

import com.company.payroll.model.Holiday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Repository
public interface HolidayRepository extends JpaRepository<Holiday, Long> {

    
    Optional<Holiday> findByHolidayDate(LocalDate date);

    
    boolean existsByHolidayDate(LocalDate date);

    
    List<Holiday> findAllByOrderByHolidayDateAsc();
}

