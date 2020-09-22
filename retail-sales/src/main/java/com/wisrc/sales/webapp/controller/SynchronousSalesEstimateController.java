package com.wisrc.sales.webapp.controller;

import com.wisrc.sales.webapp.service.SynchronousSalesEstimateService;
import com.wisrc.sales.webapp.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 外部接口获取销量预估，及其审批状态
 */
@RestController
@Api(tags = "通过外部接口获取销量预估，及其审批状态")
@RequestMapping(value = "/sales")
public class SynchronousSalesEstimateController {

    @Autowired
    private SynchronousSalesEstimateService synchronousSalesEstimateService;

    //外部接口获取销量预估，及其审批状态
    @ApiImplicitParams({
            @ApiImplicitParam(name = "shopSellerId", value = "卖家id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "msku", value = "MSKU编码", required = true, dataType = "String", paramType = "query"),
    })
    @RequestMapping(value = "/synchronous/estimate", method = RequestMethod.POST)
    @ApiOperation(value = "同步单个商品销量预估")
    public Result synchronousSingle(@RequestParam("shopSellerId") String shopSellerId, @RequestParam("msku") String msku) {
        return synchronousSalesEstimateService.synchronousSingle(shopSellerId, msku);
    }

    //外部接口获取销量预估，及其审批状态
    @RequestMapping(value = "/synchronous/estimate/regular/update", method = RequestMethod.POST)
    @ApiOperation(value = "手动同步全部商品的销量预估,建议对外部关闭！请勿前端调用）")
    public Result regularUpdate() {
        try {
            return synchronousSalesEstimateService.regularUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }
    }

}
