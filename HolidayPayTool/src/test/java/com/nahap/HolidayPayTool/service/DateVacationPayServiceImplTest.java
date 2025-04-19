package com.nahap.HolidayPayTool.service;

import com.nahap.HolidayPayTool.dto.DateVacationPayRequest;
import com.nahap.HolidayPayTool.dto.DateVacationPayResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DateVacationPayServiceImplTest {

    @Mock
    private HolidayService holidayService;

    @InjectMocks
    private DateVacationPayServiceImpl dateVacationPayService;

    private static final double DELTA = 0.01;

    @Test
    void calculateVacationPay_shouldCalculateCorrectly_whenNoHolidays() {

        DateVacationPayRequest request = new DateVacationPayRequest();
        request.setAverageSalary(50000.0);
        request.setStartDate(LocalDate.parse("2025-06-01"));
        request.setEndDate(LocalDate.parse("2025-06-14"));

        when(holidayService.getHolidaysInPeriod(any(), any())).thenReturn(Collections.emptyList());

        DateVacationPayResponse response = dateVacationPayService.calculateVacationPay(request);

        assertEquals(14, response.getTotalDays(), "Общее количество дней должно быть 14");
        assertEquals(0, response.getHolidaysCount(), "Количество праздничных дней должно быть 0");
        assertEquals(14, response.getActualVacationDays(), "Количество оплачиваемых дней должно быть 14");
        assertEquals(23890.78, response.getVacationPay(), DELTA, "Отпускные должны быть рассчитаны правильно");

        verify(holidayService).getHolidaysInPeriod(request.getStartDate(), request.getEndDate());
    }

    @Test
    void calculateVacationPay_shouldCalculateCorrectly_whenHolidaysExist() {

        DateVacationPayRequest request = new DateVacationPayRequest();
        request.setAverageSalary(50000.0);
        request.setStartDate(LocalDate.parse("2025-04-30"));
        request.setEndDate(LocalDate.parse("2025-05-10"));

        List<LocalDate> holidays = Arrays.asList(
                LocalDate.parse("2025-05-01"),
                LocalDate.parse("2025-05-09")
        );
        when(holidayService.getHolidaysInPeriod(any(), any())).thenReturn(holidays);

        DateVacationPayResponse response = dateVacationPayService.calculateVacationPay(request);

        assertEquals(11, response.getTotalDays(), "Общее количество дней должно быть 11");
        assertEquals(2, response.getHolidaysCount(), "Количество праздничных дней должно быть 2");
        assertEquals(9, response.getActualVacationDays(), "Количество оплачиваемых дней должно быть 9");
        assertEquals(15358.36, response.getVacationPay(), DELTA, "Отпускные должны быть рассчитаны правильно");
        assertEquals(2, response.getHolidays().size(), "Должно быть возвращено 2 праздничных дня");
    }
}