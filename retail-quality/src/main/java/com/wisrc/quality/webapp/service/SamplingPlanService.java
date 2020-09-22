package com.wisrc.quality.webapp.service;

import com.wisrc.quality.webapp.utils.Result;
import com.wisrc.quality.webapp.vo.samplingPlan.GetAcReVO;
import com.wisrc.quality.webapp.vo.samplingPlan.GetCodeAndQuantity;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;

public interface SamplingPlanService {


    Result getCodeAndQuantity(@Valid GetCodeAndQuantity vo, BindingResult bindingResult);

    Result getAcRe(@Valid GetAcReVO vo, BindingResult bindingResult);
}
