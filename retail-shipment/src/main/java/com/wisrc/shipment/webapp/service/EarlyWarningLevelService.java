package com.wisrc.shipment.webapp.service;

import com.wisrc.shipment.webapp.utils.Result;
import com.wisrc.shipment.webapp.vo.earlyWarningLevelAttr.BatchEarlyWarningLevelAttrVO;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;

public interface EarlyWarningLevelService {
    Result update(@Valid BatchEarlyWarningLevelAttrVO vo, BindingResult bindingResult);

    Result findAll();
}
