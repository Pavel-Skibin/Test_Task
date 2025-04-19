package com.nahap.HolidayPayTool.service;

import com.nahap.HolidayPayTool.dto.DateVacationPayRequest;
import com.nahap.HolidayPayTool.dto.DateVacationPayResponse;
import javax.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class DateVacationPayServiceImpl implements DateVacationPayService {

    private static final double AVERAGE_DAYS_PER_MONTH = 29.3;

    private final HolidayService holidayService;

    @Autowired
    public DateVacationPayServiceImpl(HolidayService holidayService) {
        this.holidayService = holidayService;
    }

    @Override
    public DateVacationPayResponse calculateVacationPay(DateVacationPayRequest request) {
        validateRequest(request);

        LocalDate startDate = request.getStartDate();
        LocalDate endDate = request.getEndDate();

        int totalDays = (int) ChronoUnit.DAYS.between(startDate, endDate) + 1;
        List<LocalDate> holidays = holidayService.getHolidaysInPeriod(startDate, endDate);
        int holidaysCount = holidays.size();
        int actualVacationDays = totalDays - holidaysCount;

        if (actualVacationDays <= 0) {
            throw new ValidationException("Все указанные дни отпуска приходятся на праздники");
        }

        double averageDailySalary = request.getAverageSalary() / AVERAGE_DAYS_PER_MONTH;
        double vacationPay = averageDailySalary * actualVacationDays;
        BigDecimal roundedPay = BigDecimal.valueOf(vacationPay)
                .setScale(2, RoundingMode.HALF_UP);

        return DateVacationPayResponse.builder()
                .vacationPay(roundedPay.doubleValue())
                .totalDays(totalDays)
                .actualVacationDays(actualVacationDays)
                .holidaysCount(holidaysCount)
                .holidays(holidays)
                .startDate(startDate)
                .endDate(endDate)
                .build();
    }

    private void validateRequest(DateVacationPayRequest request) {
        if (request.getAverageSalary() == null) {
            throw new ValidationException("Средняя зарплата не может быть пустой");
        }

        if (request.getAverageSalary() < 0) {
            throw new ValidationException("Средняя зарплата не может быть отрицательной");
        }

        if (request.getStartDate() == null) {
            throw new ValidationException("Дата начала отпуска обязательна");
        }

        if (request.getEndDate() == null) {
            throw new ValidationException("Дата окончания отпуска обязательна");
        }

        if (request.getStartDate().isAfter(request.getEndDate())) {
            throw new ValidationException("Дата начала отпуска не может быть позже даты окончания");
        }
    }
}