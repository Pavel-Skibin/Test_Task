package com.nahap.HolidayPayTool.service;


import com.nahap.HolidayPayTool.dto.VacationPayRequest;
import com.nahap.HolidayPayTool.dto.VacationPayResponse;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class VacationPayServiceImpl implements VacationPayService {

    private static final double AVERAGE_DAYS_PER_MONTH = 29.3;

    @Override
    public VacationPayResponse calculateVacationPay(VacationPayRequest request) {

        validateRequest(request);

        double averageDailySalary = request.getAverageSalary() / AVERAGE_DAYS_PER_MONTH;
        double vacationPay = averageDailySalary * request.getVacationDays();
        BigDecimal roundedPay = BigDecimal.valueOf(vacationPay)
                .setScale(2, RoundingMode.HALF_UP);

        return new VacationPayResponse(roundedPay.doubleValue());
    }

    private void validateRequest(VacationPayRequest request) {
        if (request.getAverageSalary() == null) {
            throw new ValidationException("Средняя зарплата не может быть пустой");
        }

        if (request.getAverageSalary() < 0) {
            throw new ValidationException("Средняя зарплата не может быть отрицательной");
        }

        if (request.getVacationDays() == null) {
            throw new ValidationException("Количество дней отпуска не может быть пустым");
        }

        if (request.getVacationDays() <= 0) {
            throw new ValidationException("Количество дней отпуска должно быть положительным числом");
        }
    }
}
