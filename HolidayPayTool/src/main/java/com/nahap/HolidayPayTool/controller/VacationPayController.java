package com.nahap.HolidayPayTool.controller;

import com.nahap.HolidayPayTool.dto.DateVacationPayRequest;
import com.nahap.HolidayPayTool.dto.DateVacationPayResponse;
import com.nahap.HolidayPayTool.dto.VacationPayRequest;
import com.nahap.HolidayPayTool.dto.VacationPayResponse;
import com.nahap.HolidayPayTool.service.DateVacationPayService;
import com.nahap.HolidayPayTool.service.VacationPayService;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
public class VacationPayController {

    private final VacationPayService vacationPayService;
    private final DateVacationPayService dateVacationPayService;

    @Autowired
    public VacationPayController(VacationPayService vacationPayService,
                                 DateVacationPayService dateVacationPayService) {
        this.vacationPayService = vacationPayService;
        this.dateVacationPayService = dateVacationPayService;
    }

    @GetMapping("/calculate")
    public ResponseEntity<VacationPayResponse> calculateVacationPay(
            @RequestParam @NotNull @Min(0) Double averageSalary,
            @RequestParam @NotNull @Min(1) Integer vacationDays) {

        VacationPayRequest request = new VacationPayRequest();
        request.setAverageSalary(averageSalary);
        request.setVacationDays(vacationDays);

        VacationPayResponse response = vacationPayService.calculateVacationPay(request);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/calculateByDates")
    public ResponseEntity<DateVacationPayResponse> calculateVacationPayByDates(
            @RequestParam @NotNull @Min(0) Double averageSalary,
            @RequestParam @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        DateVacationPayRequest request = new DateVacationPayRequest();
        request.setAverageSalary(averageSalary);
        request.setStartDate(startDate);
        request.setEndDate(endDate);

        DateVacationPayResponse response = dateVacationPayService.calculateVacationPay(request);

        return ResponseEntity.ok(response);
    }
}