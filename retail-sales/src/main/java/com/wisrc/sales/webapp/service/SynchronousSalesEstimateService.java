package com.wisrc.sales.webapp.service;

import com.wisrc.sales.webapp.utils.Result;

public interface SynchronousSalesEstimateService {
    Result synchronousSingle(String shopSellerId, String msku);

    Result regularUpdate();
}
