package com.nahap.HolidayPayTool.service;

import com.nahap.HolidayPayTool.dto.VacationPayRequest;
import com.nahap.HolidayPayTool.dto.VacationPayResponse;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class VacationPayServiceImplTest {

    private final VacationPayService vacationPayService = new VacationPayServiceImpl();
    private static final double DELTA = 0.01;
    @ParameterizedTest
    @CsvSource({
            "50000, 14, 23890.78",
            "30000, 7, 7167.24",
            "100000, 28, 95563.14",
            "75000, 21, 53754.27"
    })
    void calculateVacationPay_shouldReturnCorrectAmount(double averageSalary, int vacationDays, double expectedPay) {
        VacationPayRequest request = new VacationPayRequest();
        request.setAverageSalary(averageSalary);
        request.setVacationDays(vacationDays);

        VacationPayResponse response = vacationPayService.calculateVacationPay(request);

        assertEquals(expectedPay, response.getVacationPay(), DELTA,
                "Расчет отпускных должен быть: " + expectedPay);
    }
}