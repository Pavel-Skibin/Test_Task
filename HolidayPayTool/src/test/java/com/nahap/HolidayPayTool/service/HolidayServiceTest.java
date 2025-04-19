package com.nahap.HolidayPayTool.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HolidayServiceTest {

    private final HolidayService holidayService = new HolidayService();

    @ParameterizedTest
    @ValueSource(strings = {
            "2025-01-01",
            "2025-01-07",
            "2025-02-23",
            "2025-03-08",
            "2025-05-01",
            "2025-05-09",
            "2025-06-12",
            "2025-11-04"
    })
    void isHoliday_shouldReturnTrue_forHolidays(String date) {

        LocalDate holidayDate = LocalDate.parse(date);

        boolean result = holidayService.isHoliday(holidayDate);

        assertTrue(result, date + " должен быть праздником");
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "2025-01-15",
            "2025-04-10",
            "2025-07-20",
            "2025-12-15"
    })
    void isHoliday_shouldReturnFalse_forNonHolidays(String date) {

        LocalDate nonHolidayDate = LocalDate.parse(date);

        boolean result = holidayService.isHoliday(nonHolidayDate);

        assertFalse(result, date + " не должен быть праздником");
    }

    @Test
    void getHolidaysInPeriod_shouldReturnCorrectList_whenPeriodContainsHolidays() {

        LocalDate startDate = LocalDate.parse("2025-04-28");
        LocalDate endDate = LocalDate.parse("2025-05-10");

        List<LocalDate> holidays = holidayService.getHolidaysInPeriod(startDate, endDate);

        assertEquals(2, holidays.size(), "Период должен содержать 2 праздника: 1 и 9 мая");
        assertTrue(holidays.contains(LocalDate.parse("2025-05-01")), "Список должен содержать 1 мая");
        assertTrue(holidays.contains(LocalDate.parse("2025-05-09")), "Список должен содержать 9 мая");
    }

    @Test
    void getHolidaysInPeriod_shouldReturnEmptyList_whenPeriodContainsNoHolidays() {

        LocalDate startDate = LocalDate.parse("2025-04-10");
        LocalDate endDate = LocalDate.parse("2025-04-20");

        List<LocalDate> holidays = holidayService.getHolidaysInPeriod(startDate, endDate);

        assertTrue(holidays.isEmpty(), "Список праздников должен быть пустым для периода без праздников");
    }

    @Test
    void getHolidaysInPeriod_shouldWorkCorrectly_forOneDayPeriod() {

        LocalDate holidayDate = LocalDate.parse("2025-05-01");

        List<LocalDate> holidays = holidayService.getHolidaysInPeriod(holidayDate, holidayDate);

        assertEquals(1, holidays.size(), "Однодневный период с праздником должен вернуть 1 праздник");
        assertEquals(holidayDate, holidays.get(0), "Возвращаемый праздник должен совпадать с датой");
    }
}