package com.nahap.HolidayPayTool.service;


import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.MonthDay;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class HolidayService {


    private static final Set<MonthDay> HOLIDAYS = new HashSet<>();

    static {

        HOLIDAYS.add(MonthDay.of(1, 1));
        HOLIDAYS.add(MonthDay.of(1, 2));
        HOLIDAYS.add(MonthDay.of(1, 3));
        HOLIDAYS.add(MonthDay.of(1, 4));
        HOLIDAYS.add(MonthDay.of(1, 5));
        HOLIDAYS.add(MonthDay.of(1, 6));
        HOLIDAYS.add(MonthDay.of(1, 7));
        HOLIDAYS.add(MonthDay.of(1, 8));

        HOLIDAYS.add(MonthDay.of(2, 23));
        HOLIDAYS.add(MonthDay.of(3, 8));
        HOLIDAYS.add(MonthDay.of(5, 1));
        HOLIDAYS.add(MonthDay.of(5, 9));
        HOLIDAYS.add(MonthDay.of(6, 12));
        HOLIDAYS.add(MonthDay.of(11, 4));
    }

    public boolean isHoliday(LocalDate date) {
        return HOLIDAYS.contains(MonthDay.from(date));
    }


    public List<LocalDate> getHolidaysInPeriod(LocalDate startDate, LocalDate endDate) {
        List<LocalDate> holidays = new ArrayList<>();
        LocalDate currentDate = startDate;

        while (!currentDate.isAfter(endDate)) {
            if (isHoliday(currentDate)) {
                holidays.add(currentDate);
            }
            currentDate = currentDate.plusDays(1);
        }

        return holidays;
    }

}