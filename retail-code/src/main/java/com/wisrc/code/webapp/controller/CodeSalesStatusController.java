package com.wisrc.code.webapp.controller;

import com.wisrc.code.webapp.service.CodeSalesStatusService;
import com.wisrc.code.webapp.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags = "销售状态码表管理")
@RequestMapping(value = "/code")
public class CodeSalesStatusController {
    private final Logger logger = LoggerFactory.getLogger(CodeSalesStatusController.class);

    @Autowired
    private CodeSalesStatusService codeSalesStatusService;

    @ApiOperation(value = "查询所有信息", notes = "")
    @RequestMapping(value = "/codeSalesStatus", method = RequestMethod.GET)
    public Result findAll() {
        return codeSalesStatusService.findAll();
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "saleStatusCd", value = "状态码", paramType = "query", dataType = "int", required = true),
            @ApiImplicitParam(name = "saleStatusDesc", value = "中文名", paramType = "query", dataType = "String", required = true),
    })
    @ApiOperation(value = "编辑", notes = "")
    @RequestMapping(value = "/codeSalesStatus", method = RequestMethod.PUT)
    public Result update(@RequestParam("saleStatusCd") Integer saleStatusCd,
                         @RequestParam("saleStatusDesc") String saleStatusDesc) {
        return codeSalesStatusService.update(saleStatusCd, saleStatusDesc);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "saleStatusCd", value = "状态码", paramType = "query", dataType = "int", required = true),
            @ApiImplicitParam(name = "saleStatusDesc", value = "中文名", paramType = "query", dataType = "String", required = true),
    })
    @ApiOperation(value = "新增", notes = "")
    @RequestMapping(value = "/codeSalesStatus", method = RequestMethod.POST)
    public Result insert(@RequestParam("saleStatusCd") Integer saleStatusCd,
                         @RequestParam("saleStatusDesc") String saleStatusDesc) {
        return codeSalesStatusService.insert(saleStatusCd, saleStatusDesc);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "saleStatusCd", value = "状态码", paramType = "path", dataType = "int", required = true),
    })
    @ApiOperation(value = "删除", notes = "")
    @RequestMapping(value = "/codeSalesStatus/{saleStatusCd}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable("saleStatusCd") Integer saleStatusCd) {
        return codeSalesStatusService.delete(saleStatusCd);
    }

    @ApiOperation(value = "获取采购计划生成状态范围", notes = "")
    @RequestMapping(value = "/sales/purchase/plan", method = RequestMethod.GET)
    public Result purchasePlanStatus() {
        return codeSalesStatusService.purchasePlanStatus();
    }
}
