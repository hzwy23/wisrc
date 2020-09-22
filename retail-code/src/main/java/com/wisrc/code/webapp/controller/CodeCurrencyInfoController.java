package com.wisrc.code.webapp.controller;

import com.wisrc.code.webapp.service.CodeCurrencyInfoService;
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
@Api(tags = "币种码表")
@RequestMapping(value = "/code")
public class CodeCurrencyInfoController {
    private final Logger logger = LoggerFactory.getLogger(CodeCurrencyInfoController.class);

    @Autowired
    private CodeCurrencyInfoService codeCurrencyInfoService;

    @ApiOperation(value = "查询所有信息", notes = "")
    @RequestMapping(value = "/codeCurrencyInfo", method = RequestMethod.GET)
    public Result findAll() {
        return codeCurrencyInfoService.findAll();
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "isoCurrencyCd", value = "状态码", paramType = "query", dataType = "String", required = true),
            @ApiImplicitParam(name = "isoCurrencyEnglish", value = "英文名", paramType = "query", dataType = "String", required = true),
            @ApiImplicitParam(name = "isoCurrencyName", value = "中文名", paramType = "query", dataType = "String", required = true),
    })
    @ApiOperation(value = "新增", notes = "")
    @RequestMapping(value = "/codeCurrencyInfo", method = RequestMethod.POST)
    public Result insert(@RequestParam("isoCurrencyCd") String isoCurrencyCd,
                         @RequestParam("isoCurrencyEnglish") String isoCurrencyEnglish,
                         @RequestParam("isoCurrencyName") String isoCurrencyName) {
        return codeCurrencyInfoService.insert(isoCurrencyCd, isoCurrencyEnglish, isoCurrencyName);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "isoCurrencyCd", value = "状态码", paramType = "query", dataType = "String", required = true),
            @ApiImplicitParam(name = "isoCurrencyEnglish", value = "英文名", paramType = "query", dataType = "String", required = true),
            @ApiImplicitParam(name = "isoCurrencyName", value = "中文名", paramType = "query", dataType = "String", required = true),
            @ApiImplicitParam(name = "statusCd", value = "状态", paramType = "query", dataType = "int", required = true),
    })
    @ApiOperation(value = "编辑", notes = "")
    @RequestMapping(value = "/codeCurrencyInfo", method = RequestMethod.PUT)
    public Result update(@RequestParam("isoCurrencyCd") String isoCurrencyCd,
                         @RequestParam("isoCurrencyEnglish") String isoCurrencyEnglish,
                         @RequestParam("isoCurrencyName") String isoCurrencyName,
                         @RequestParam("statusCd") Integer statusCd) {
        return codeCurrencyInfoService.update(isoCurrencyCd, isoCurrencyEnglish, isoCurrencyName, statusCd);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "isoCurrencyCd", value = "状态码", paramType = "path", dataType = "String", required = true),
    })
    @ApiOperation(value = "删除", notes = "")
    @RequestMapping(value = "/codeCurrencyInfo/{isoCurrencyCd}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable("isoCurrencyCd") String isoCurrencyCd) {
        return codeCurrencyInfoService.delete(isoCurrencyCd);
    }
}
