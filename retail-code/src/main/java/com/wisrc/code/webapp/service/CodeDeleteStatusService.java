package com.wisrc.code.webapp.service;

import com.wisrc.code.webapp.utils.Result;

public interface CodeDeleteStatusService {
    Result findAll();

    Result update(Integer deleteStatus, String deleteStatusDesc);

    Result insert(Integer deleteStatus, String deleteStatusDesc);

    Result delete(Integer deleteStatus);
}
