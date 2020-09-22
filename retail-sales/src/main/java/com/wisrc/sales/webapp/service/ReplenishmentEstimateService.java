package com.wisrc.sales.webapp.service;

import com.wisrc.sales.webapp.utils.Result;
import com.wisrc.sales.webapp.vo.replenishmentEstimate.get.GetReplenishmentEstimateListVO;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;

public interface ReplenishmentEstimateService {
    Result replenishment(@Valid GetReplenishmentEstimateListVO vo, BindingResult result);
}
