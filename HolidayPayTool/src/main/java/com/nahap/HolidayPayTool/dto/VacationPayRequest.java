package com.nahap.HolidayPayTool.dto;


import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VacationPayRequest {


    @NotNull(message = "Средняя зарплата не может быть пустой")
    @Min(value = 0, message = "Средняя зарплата не может быть отрицательной")
    private Double averageSalary;

    @NotNull(message = "Количество дней отпуска не может быть пустым")
    @Min(value = 1, message = "Количество дней отпуска должно быть положительным числом")
    private Integer vacationDays;

}
