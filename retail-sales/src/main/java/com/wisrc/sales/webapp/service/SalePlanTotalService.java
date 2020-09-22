package com.wisrc.sales.webapp.service;

import com.wisrc.sales.webapp.vo.SelectTotalInfoVO;

import java.util.List;

public interface SalePlanTotalService {
    List<SelectTotalInfoVO> getTotal(String shopId, String msku, String asin,
                                     String directorEmployeeId, String chargeEmployeeId,
                                     String startMonth, String endMonth, String salesStatusCd, String userId);
}
