package com.wisrc.quality.webapp.controller;

import com.wisrc.quality.webapp.service.SamplingPlanService;
import com.wisrc.quality.webapp.utils.Result;
import com.wisrc.quality.webapp.vo.samplingPlan.GetAcReVO;
import com.wisrc.quality.webapp.vo.samplingPlan.GetCodeAndQuantity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Api(tags = "抽样计划")
@RequestMapping(value = "/quality")
public class SamplingPlanController {
    @Autowired
    private SamplingPlanService smplingPlanService;

    @ApiOperation(value = "查询抽样字码与抽样数", notes = "")
    @RequestMapping(value = "/sampling/code/quantity", method = RequestMethod.GET)
    public Result findOne(@Valid GetCodeAndQuantity vo, BindingResult bindingResult) {
        return smplingPlanService.getCodeAndQuantity(vo, bindingResult);
    }

    @ApiOperation(value = "查询抽样国标AC，Re", notes = "")
    @RequestMapping(value = "/sampling/ac/re", method = RequestMethod.GET)
    public Result getAcRe(@Valid GetAcReVO vo, BindingResult bindingResult) {
        return smplingPlanService.getAcRe(vo, bindingResult);
    }

}
