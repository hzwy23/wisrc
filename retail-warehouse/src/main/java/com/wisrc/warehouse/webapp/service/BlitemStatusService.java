package com.wisrc.warehouse.webapp.service;

import com.wisrc.warehouse.webapp.utils.Result;

public interface BlitemStatusService {
    Result findAll();

    Result findById(String statusCd);
}
