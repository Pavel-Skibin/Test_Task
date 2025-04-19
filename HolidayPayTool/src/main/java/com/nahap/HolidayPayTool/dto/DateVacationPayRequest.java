package com.nahap.HolidayPayTool.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class DateVacationPayRequest {
    @NotNull(message = "Средняя зарплата не может быть пустой")
    @Min(value = 0, message = "Средняя зарплата не может быть отрицательной")
    private Double averageSalary;

    @NotNull(message = "Дата начала отпуска обязательна")
    private LocalDate startDate;

    @NotNull(message = "Дата окончания отпуска обязательна")
    private LocalDate endDate;
}