package com.company.payroll.service;

import com.company.payroll.dto.ApiResponse;
import com.company.payroll.dto.HolidayRequest;
import com.company.payroll.dto.HolidayResponse;
import com.company.payroll.model.Holiday;
import com.company.payroll.repository.HolidayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class HolidayService {

    private final HolidayRepository holidayRepository;

    @Autowired
    public HolidayService(HolidayRepository holidayRepository) {
        this.holidayRepository = holidayRepository;
    }

    
    private HolidayResponse convertToResponse(Holiday holiday) {
        return new HolidayResponse(
                holiday.getId(),
                holiday.getHolidayName(),
                holiday.getHolidayDate(),
                holiday.getDescription(),
                holiday.getCreatedAt(),
                holiday.getUpdatedAt()
        );
    }

    
    public ApiResponse<HolidayResponse> addHoliday(HolidayRequest request) {
        
        if (request.getHolidayName() == null || request.getHolidayName().trim().isEmpty()) {
            return new ApiResponse<>(false, "Holiday name is required");
        }

        if (request.getHolidayDate() == null) {
            return new ApiResponse<>(false, "Holiday date is required");
        }

        
        if (holidayRepository.existsByHolidayDate(request.getHolidayDate())) {
            return new ApiResponse<>(false, "Holiday already exists for this date");
        }

        
        Holiday holiday = new Holiday();
        holiday.setHolidayName(request.getHolidayName().trim());
        holiday.setHolidayDate(request.getHolidayDate());
        holiday.setDescription(request.getDescription() != null ? request.getDescription().trim() : null);

        Holiday savedHoliday = holidayRepository.save(holiday);
        return new ApiResponse<>(true, "Holiday added successfully", convertToResponse(savedHoliday));
    }

    
    public ApiResponse<List<HolidayResponse>> getAllHolidays() {
        List<Holiday> holidays = holidayRepository.findAllByOrderByHolidayDateAsc();
        List<HolidayResponse> responses = holidays.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
        return new ApiResponse<>(true, "Holidays retrieved successfully", responses);
    }

    
    public ApiResponse<Void> deleteHoliday(Long id) {
        
        if (id == null) {
            return new ApiResponse<>(false, "Holiday ID is required");
        }

        
        Optional<Holiday> holidayOptional = holidayRepository.findById(id);
        if (holidayOptional.isEmpty()) {
            return new ApiResponse<>(false, "Holiday not found");
        }

        
        holidayRepository.deleteById(id);
        return new ApiResponse<>(true, "Holiday deleted successfully");
    }
}

