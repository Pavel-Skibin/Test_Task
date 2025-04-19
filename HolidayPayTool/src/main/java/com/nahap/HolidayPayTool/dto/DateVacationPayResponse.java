package com.nahap.HolidayPayTool.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class DateVacationPayResponse {
    private Double vacationPay;
    private Integer totalDays;
    private Integer actualVacationDays;
    private Integer holidaysCount;
    private List<LocalDate> holidays;
    private LocalDate startDate;
    private LocalDate endDate;
}