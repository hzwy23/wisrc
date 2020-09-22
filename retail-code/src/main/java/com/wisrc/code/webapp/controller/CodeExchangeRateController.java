package com.wisrc.code.webapp.controller;

import com.wisrc.code.webapp.vo.codeExchangeRate.add.AddCodeExchangeRateVO;
import com.wisrc.code.webapp.vo.codeExchangeRate.get.GetCodeExchangeRateVO;
import com.wisrc.code.webapp.vo.codeExchangeRate.set.SetCodeExchangeRateVO;
import com.wisrc.code.webapp.service.CodeExchangeRateService;
import com.wisrc.code.webapp.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/code")
@Api(tags = "货币汇率")
public class CodeExchangeRateController {
    private final Logger logger = LoggerFactory.getLogger(CodeExchangeRateController.class);

    @Autowired
    private CodeExchangeRateService codeExchangeRateService;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-ID", value = "当前用户", paramType = "header", dataType = "String", required = true)
    })
    @ApiOperation(value = "新增", notes = "")
    @RequestMapping(value = "/code/exchange/rate", method = RequestMethod.POST)
    public Result insert(@Valid @RequestBody AddCodeExchangeRateVO vo,
                         BindingResult bindingResult,
                         @RequestHeader("X-AUTH-ID") String userId) {
        return codeExchangeRateService.insert(vo,
                bindingResult,
                userId);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-ID", value = "当前用户", paramType = "header", dataType = "String", required = true)
    })
    @ApiOperation(value = "更新", notes = "")
    @RequestMapping(value = "/code/exchange/rate", method = RequestMethod.PUT)
    public Result update(@Valid @RequestBody SetCodeExchangeRateVO vo,
                         BindingResult bindingResult,
                         @RequestHeader("X-AUTH-ID") String userId) {
        return codeExchangeRateService.update(vo,
                bindingResult,
                userId);
    }

    @ApiOperation(value = "uuid查询", notes = "")
    @RequestMapping(value = "/code/exchange/rate/{uuid}", method = RequestMethod.GET)
    public Result getByUuid(@PathVariable String uuid) {
        return codeExchangeRateService.getByUuid(uuid);
    }

    @ApiOperation(value = "删除", notes = "")
    @RequestMapping(value = "/code/exchange/rate/{uuid}", method = RequestMethod.DELETE)
    public Result deleteByUuid(@PathVariable String uuid) {
        return codeExchangeRateService.deleteByUuid(uuid);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页（小于1或者null为不分页）", required = false, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "单页条数（小于1或者null为不分页）", required = false, dataType = "Integer", paramType = "query")
    })
    @ApiOperation(value = "精准条件查询", notes = "")
    @RequestMapping(value = "/code/exchange/rate", method = RequestMethod.GET)
    public Result find(GetCodeExchangeRateVO vo, Integer pageNum, Integer pageSize) {
        return codeExchangeRateService.find(vo, pageNum, pageSize);
    }

}
