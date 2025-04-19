package com.nahap.HolidayPayTool.service;

import com.nahap.HolidayPayTool.dto.VacationPayRequest;
import com.nahap.HolidayPayTool.dto.VacationPayResponse;

public interface VacationPayService {

    VacationPayResponse calculateVacationPay(VacationPayRequest vacationPayRequest);

}
