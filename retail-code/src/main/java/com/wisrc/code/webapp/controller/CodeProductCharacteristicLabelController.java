package com.wisrc.code.webapp.controller;

import com.wisrc.code.webapp.service.CodePCLabelService;
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
@Api(tags = "产品特性标签码表")
@RequestMapping(value = "/code")
public class CodeProductCharacteristicLabelController {
    private final Logger logger = LoggerFactory.getLogger(CodeProductCharacteristicLabelController.class);

    @Autowired
    private CodePCLabelService codePCLabelService;

    @ApiOperation(value = "查询所有信息", notes = "")
    @RequestMapping(value = "/codePCLabel", method = RequestMethod.GET)
    public Result findAll() {
        return codePCLabelService.findAll();
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "productLabelCd", value = "状态码", paramType = "query", dataType = "int", required = true),
            @ApiImplicitParam(name = "productLabelDesc", value = "中文名", paramType = "query", dataType = "String", required = true),
    })
    @ApiOperation(value = "编辑", notes = "")
    @RequestMapping(value = "/codePCLabel", method = RequestMethod.PUT)
    public Result update(@RequestParam("productLabelCd") Integer productLabelCd,
                         @RequestParam("productLabelDesc") String productLabelDesc) {
        return codePCLabelService.update(productLabelCd, productLabelDesc);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "productLabelCd", value = "状态码", paramType = "query", dataType = "int", required = true),
            @ApiImplicitParam(name = "productLabelDesc", value = "中文名", paramType = "query", dataType = "String", required = true),
    })
    @ApiOperation(value = "新增", notes = "")
    @RequestMapping(value = "/codePCLabel", method = RequestMethod.POST)
    public Result insert(@RequestParam("productLabelCd") Integer productLabelCd,
                         @RequestParam("productLabelDesc") String productLabelDesc) {
        return codePCLabelService.insert(productLabelCd, productLabelDesc);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "productLabelCd", value = "状态码", paramType = "path", dataType = "int", required = true),
    })
    @ApiOperation(value = "删除", notes = "")
    @RequestMapping(value = "/codePCLabel/{productLabelCd}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable("productLabelCd") Integer productLabelCd) {
        return codePCLabelService.delete(productLabelCd);
    }
}
