package com.wisrc.code.webapp.service;

import com.wisrc.code.webapp.utils.Result;

public interface CodePCLabelService {
    Result findAll();

    Result update(Integer productLabelCd, String productLabelDesc);

    Result insert(Integer productLabelCd, String productLabelDesc);

    Result delete(Integer productLabelCd);
}
