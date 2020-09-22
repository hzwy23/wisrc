package com.wisrc.code.webapp.service;

import com.wisrc.code.webapp.utils.Result;

public interface CodeSalesStatusService {
    Result findAll();

    Result update(Integer saleStatusCd, String saleStatusDesc);

    Result insert(Integer saleStatusCd, String saleStatusDesc);

    Result delete(Integer saleStatusCd);

    Result purchasePlanStatus();
}
