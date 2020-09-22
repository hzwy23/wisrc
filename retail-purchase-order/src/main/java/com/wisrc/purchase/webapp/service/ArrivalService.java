package com.wisrc.purchase.webapp.service;

import com.wisrc.purchase.webapp.utils.Result;
import com.wisrc.purchase.webapp.vo.inspection.ArrivalExcelVo;
import com.wisrc.purchase.webapp.vo.inspection.ArrivalPageVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ArrivalService {
    Result arrivalList(ArrivalPageVo inspectionPageVo);

    Result excel(ArrivalExcelVo inspectionExcelVo, HttpServletRequest request, HttpServletResponse response);

    Result getArrival(String arrivalId);
}
