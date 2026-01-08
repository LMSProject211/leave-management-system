package com.company.payroll.controller;

import com.company.payroll.dto.ApiResponse;
import com.company.payroll.dto.HolidayRequest;
import com.company.payroll.dto.HolidayResponse;
import com.company.payroll.service.HolidayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/holidays")
public class HolidayController extends BaseController {

    private final HolidayService holidayService;

    @Autowired
    public HolidayController(HolidayService holidayService) {
        this.holidayService = holidayService;
    }

    
    @PostMapping
    public ResponseEntity<ApiResponse<HolidayResponse>> addHoliday(@RequestBody HolidayRequest request) {
        ApiResponse<HolidayResponse> response = holidayService.addHoliday(request);

        if (response.isSuccess()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    
    @GetMapping
    public ResponseEntity<ApiResponse<List<HolidayResponse>>> getAllHolidays() {
        ApiResponse<List<HolidayResponse>> response = holidayService.getAllHolidays();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteHoliday(@PathVariable Long id) {
        ApiResponse<Void> response = holidayService.deleteHoliday(id);

        if (response.isSuccess()) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}

