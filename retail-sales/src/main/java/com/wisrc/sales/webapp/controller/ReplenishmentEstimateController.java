package com.wisrc.sales.webapp.controller;

import com.wisrc.sales.webapp.service.ReplenishmentEstimateService;
import com.wisrc.sales.webapp.utils.Result;
import com.wisrc.sales.webapp.vo.replenishmentEstimate.get.GetReplenishmentEstimateListVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Api(tags = "外部接口：msku补货预估管理")
@RequestMapping(value = "/sales")
public class ReplenishmentEstimateController {

    @Autowired
    private ReplenishmentEstimateService replenishmentEstimateService;

    @RequestMapping(value = "/replenishment/estimate", method = RequestMethod.POST)
    @ApiOperation(value = "批量判断时间段内msku的预估数量与库存数量关系")
    public Result replenishment(@Valid @RequestBody GetReplenishmentEstimateListVO vo, BindingResult result) {
        return replenishmentEstimateService.replenishment(vo, result);
    }
}


