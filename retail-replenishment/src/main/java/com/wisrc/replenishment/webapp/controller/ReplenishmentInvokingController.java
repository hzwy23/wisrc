package com.wisrc.replenishment.webapp.controller;

import com.wisrc.replenishment.webapp.service.ReplenishmentMskuService;
import com.wisrc.replenishment.webapp.service.SystemService;
import com.wisrc.replenishment.webapp.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/replenishment/invoking")
@RestController
@Api(value = "/invoking", description = "外部接口调用控制器", tags = "外部接口")
public class ReplenishmentInvokingController {
    @Autowired
    private ReplenishmentMskuService replenishmentMskuService;
    @Autowired
    private SystemService systemService;

    @ApiOperation(value = "销售状态选择框", notes = "")
    @RequestMapping(value = "/sales/status/selector", method = RequestMethod.GET)
    public Result getSalesPlanSelector() {
        return replenishmentMskuService.getSaleStatusSelector();
    }

    @ApiOperation(value = "店铺选择框", notes = "")
    @RequestMapping(value = "/shop/selector", method = RequestMethod.GET)
    public Result getShopSelector() {
        return replenishmentMskuService.getShopSelector();
    }

    @ApiOperation(value = "获取销售状态", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mskuId", value = "商品ID", paramType = "path", dataType = "String", required = true)
    })
    @RequestMapping(value = "/sales/plan/{mskuId}", method = RequestMethod.GET)
    public Result getSalesPlan(@PathVariable("mskuId") String mskuId) {
        return replenishmentMskuService.getSalesPlan(mskuId);
    }

    @ApiOperation(value = "人员选择框", notes = "")
    @RequestMapping(value = "/employee/selector", method = RequestMethod.GET)
    public Result getEmployeeSelector() {
        return systemService.getEmployySelector();
    }
}
