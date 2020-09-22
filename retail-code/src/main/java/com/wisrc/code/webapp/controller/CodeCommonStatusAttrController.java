package com.wisrc.code.webapp.controller;


import com.wisrc.code.webapp.service.CodeCommonStatusAttrService;
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
@Api(tags = "公共状态码表")
@RequestMapping(value = "/code")
public class CodeCommonStatusAttrController {

    private final Logger logger = LoggerFactory.getLogger(CodeCommonStatusAttrController.class);

    @Autowired
    private CodeCommonStatusAttrService codeCommonStatusAttrService;

    @ApiOperation(value = "查询所有信息", notes = "")
    @RequestMapping(value = "/codeCommonStatusAttr", method = RequestMethod.GET)
    public Result findAll() {
        return codeCommonStatusAttrService.findAll();
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "statusCd", value = "状态码", paramType = "query", dataType = "int", required = true),
            @ApiImplicitParam(name = "statusDesc", value = "状态描述", paramType = "query", dataType = "String", required = true),
    })
    @ApiOperation(value = "新增", notes = "")
    @RequestMapping(value = "/codeCommonStatusAttr", method = RequestMethod.POST)
    public Result insert(@RequestParam("statusCd") Integer statusCd, @RequestParam("statusDesc") String statusDesc) {
        return codeCommonStatusAttrService.insert(statusCd, statusDesc);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "statusCd", value = "状态码", paramType = "query", dataType = "int", required = true),
            @ApiImplicitParam(name = "statusDesc", value = "状态描述", paramType = "query", dataType = "String", required = true),
    })
    @ApiOperation(value = "编辑", notes = "")
    @RequestMapping(value = "/codeCommonStatusAttr", method = RequestMethod.PUT)
    public Result update(@RequestParam("statusCd") Integer statusCd, @RequestParam("statusDesc") String statusDesc) {
        return codeCommonStatusAttrService.update(statusCd, statusDesc);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "statusCd", value = "状态码", paramType = "path", dataType = "int", required = true),
    })
    @ApiOperation(value = "删除", notes = "")
    @RequestMapping(value = "/codeCommonStatusAttr/{statusCd}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable("statusCd") Integer statusCd) {
        return codeCommonStatusAttrService.delete(statusCd);
    }


}
