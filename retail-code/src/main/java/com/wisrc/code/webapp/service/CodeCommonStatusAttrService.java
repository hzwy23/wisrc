package com.wisrc.code.webapp.service;

import com.wisrc.code.webapp.utils.Result;

public interface CodeCommonStatusAttrService {
    Result findAll();

    Result update(Integer statusCd, String statusDesc);

    Result insert(Integer statusCd, String statusDesc);

    Result delete(Integer statusCd);
}
