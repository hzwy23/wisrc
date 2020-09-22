package com.wisrc.shipment.webapp.controller;

import com.wisrc.shipment.webapp.service.SmartReplenishmentService;
import com.wisrc.shipment.webapp.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "MSKU智能补货提醒列表")
@RequestMapping(value = "/shipment")
public class SmartReplenishmentController {
    @Autowired
    private SmartReplenishmentService smartReplenishmentService;

    @RequestMapping(value = "/smart/replenishment/warning", method = RequestMethod.POST)
    @ApiOperation(value = "生成智能补货提醒列表")
    public Result getWarning() {
        return smartReplenishmentService.addWarning();
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "shopId", value = "店铺id", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "earlyWarningLevelDesc", value = "预警等级", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "salesStatusCd", value = "销售状态", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "keyWords", value = "关键字（MSKU/库存SKU/产品名称）", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "pageNum", value = "当前页(null或者小于1为全查询，不分页)", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "单页条数(null或者小于1为全查询，不分页)", required = false, dataType = "int", paramType = "query")
    })
    @RequestMapping(value = "/smart/replenishment/warning", method = RequestMethod.GET)
    @ApiOperation(value = "查询智能补货提醒列表")
    public Result getWarning(
            String shopId,
            String earlyWarningLevelDesc,
            Integer salesStatusCd,
            String keyWords,
            Integer pageNum,
            Integer pageSize
    ) {
        return smartReplenishmentService.getWarning(shopId, earlyWarningLevelDesc, salesStatusCd, keyWords, pageNum, pageSize);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "defaultSecurityStockDays", value = "安全库存天数", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "earlyWarningDays", value = "预警天数", required = true, dataType = "int", paramType = "query")
    })
    @RequestMapping(value = "/smart/replenishment/warning/stock/days", method = RequestMethod.PUT)
    @ApiOperation(value = "编辑默认预警与安全库存天数")
    public Result updateDefaultSecurityAlertDaysInfo(Integer defaultSecurityStockDays, Integer earlyWarningDays) {
        return smartReplenishmentService.updateDefaultSecurityAlertDaysInfo(defaultSecurityStockDays, earlyWarningDays);
    }

    @RequestMapping(value = "/smart/replenishment/warning/stock/days", method = RequestMethod.GET)
    @ApiOperation(value = "查询默认预警与安全库存天数")
    public Result getDefaultSecurityAlertDaysInfo() {
        return smartReplenishmentService.getDefaultSecurityAlertDaysInfo();
    }

    @RequestMapping(value = "/smart/replenishment/waringLevel", method = RequestMethod.GET)
    @ApiOperation(value = "获取捕获预警列表的预警等级")
    public Result getWaringLevel() {
        return smartReplenishmentService.getWaringLevel();
    }


}
