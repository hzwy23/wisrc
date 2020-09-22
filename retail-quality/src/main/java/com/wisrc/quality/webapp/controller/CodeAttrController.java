package com.wisrc.quality.webapp.controller;

import com.wisrc.quality.webapp.service.CodeAttrService;
import com.wisrc.quality.webapp.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.List;

@RestController
@Api(tags = "码表信息")
@RequestMapping(value = "/quality")
public class CodeAttrController {
    @Autowired
    private CodeAttrService codeAttrService;


    @ApiOperation(value = "检验状态码表", notes = "")
    @RequestMapping(value = "/inspection/status", method = RequestMethod.GET)
    public Result getISA() {
        List<LinkedHashMap> list = codeAttrService.getISA();
        return Result.success(list);
    }


    @ApiOperation(value = "验货方式码表", notes = "")
    @RequestMapping(value = "/inspection/method", method = RequestMethod.GET)
    public Result getIMA() {
        List<LinkedHashMap> list = codeAttrService.getIMA();
        return Result.success(list);
    }


    @ApiOperation(value = "检验类型码表", notes = "")
    @RequestMapping(value = "/inspection/type", method = RequestMethod.GET)
    public Result getITA() {
        List<LinkedHashMap> list = codeAttrService.getITA();
        return Result.success(list);
    }

    @ApiOperation(value = "检验水平码表", notes = "")
    @RequestMapping(value = "/inspection/level", method = RequestMethod.GET)
    public Result getILA() {
        List<LinkedHashMap> list = codeAttrService.getILA();
        return Result.success(list);
    }


    @ApiOperation(value = "抽样方案（标准）码表", notes = "")
    @RequestMapping(value = "/inspection/sample/plan", method = RequestMethod.GET)
    public Result getISPA() {
        List<LinkedHashMap> list = codeAttrService.getISPA();
        return Result.success(list);
    }


    @ApiOperation(value = "更改理由码表", notes = "")
    @RequestMapping(value = "/inspection/change/reason", method = RequestMethod.GET)
    public Result getICRA() {
        List<LinkedHashMap> list = codeAttrService.getICRA();
        return Result.success(list);
    }


    @ApiOperation(value = "结论码表", notes = "")
    @RequestMapping(value = "/inspection/conclusion", method = RequestMethod.GET)
    public Result getICA() {
        List<LinkedHashMap> list = codeAttrService.getICA();
        return Result.success(list);
    }


    @ApiOperation(value = "最终判定码表", notes = "")
    @RequestMapping(value = "/inspection/final/determination", method = RequestMethod.GET)
    public Result getIFDA() {
        List<LinkedHashMap> list = codeAttrService.getIFDA();
        return Result.success(list);
    }


    @ApiOperation(value = "来源类型码表", notes = "")
    @RequestMapping(value = "/inspection/source/type", method = RequestMethod.GET)
    public Result getISTA() {
        List<LinkedHashMap> list = codeAttrService.getISTA();
        return Result.success(list);
    }


    @ApiOperation(value = "检验项目码表", notes = "")
    @RequestMapping(value = "/inspection/items", method = RequestMethod.GET)
    public Result getIIA() {
        List<LinkedHashMap> list = codeAttrService.getIIA();
        return Result.success(list);
    }


    @ApiOperation(value = "项目检验结果码表", notes = "")
    @RequestMapping(value = "/inspection/items/result", method = RequestMethod.GET)
    public Result getIIRA() {
        List<LinkedHashMap> list = codeAttrService.getIIRA();
        return Result.success(list);
    }


    @ApiOperation(value = "判定 码表", notes = "")
    @RequestMapping(value = "/inspection/judgment", method = RequestMethod.GET)
    public Result getIJA() {
        List<LinkedHashMap> list = codeAttrService.getIJA();
        return Result.success(list);
    }

}
