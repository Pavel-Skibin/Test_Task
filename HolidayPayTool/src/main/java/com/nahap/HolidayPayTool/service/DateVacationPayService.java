package com.nahap.HolidayPayTool.service;

import com.nahap.HolidayPayTool.dto.DateVacationPayRequest;
import com.nahap.HolidayPayTool.dto.DateVacationPayResponse;

public interface DateVacationPayService {
    DateVacationPayResponse calculateVacationPay(DateVacationPayRequest request);
}