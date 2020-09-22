package com.wisrc.code.webapp.controller;

import com.wisrc.code.webapp.service.CodeDeleteStatusService;
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
@Api(tags = "删除标识码表")
@RequestMapping(value = "/code")
public class CodeDeleteStatusController {
    private final Logger logger = LoggerFactory.getLogger(CodeDeleteStatusController.class);

    @Autowired
    private CodeDeleteStatusService codeDeleteStatusService;

    @ApiOperation(value = "查询所有信息", notes = "")
    @RequestMapping(value = "/codeDeleteStatus", method = RequestMethod.GET)
    public Result findAll() {
        return codeDeleteStatusService.findAll();
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "deleteStatus", value = "状态码", paramType = "query", dataType = "int", required = true),
            @ApiImplicitParam(name = "deleteStatusDesc", value = "中文名", paramType = "query", dataType = "String", required = true),
    })
    @ApiOperation(value = "新增", notes = "")
    @RequestMapping(value = "/codeDeleteStatus", method = RequestMethod.PUT)
    public Result update(@RequestParam("deleteStatus") Integer deleteStatus,
                         @RequestParam("deleteStatusDesc") String deleteStatusDesc) {
        return codeDeleteStatusService.update(deleteStatus, deleteStatusDesc);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "deleteStatus", value = "状态码", paramType = "query", dataType = "int", required = true),
            @ApiImplicitParam(name = "deleteStatusDesc", value = "中文名", paramType = "query", dataType = "String", required = true),
    })
    @ApiOperation(value = "编辑", notes = "")
    @RequestMapping(value = "/codeDeleteStatus", method = RequestMethod.POST)
    public Result insert(@RequestParam("deleteStatus") Integer deleteStatus,
                         @RequestParam("deleteStatusDesc") String deleteStatusDesc) {
        return codeDeleteStatusService.insert(deleteStatus, deleteStatusDesc);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "deleteStatus", value = "状态码", paramType = "path", dataType = "int", required = true),
    })
    @ApiOperation(value = "删除", notes = "")
    @RequestMapping(value = "/codeDeleteStatus/{deleteStatus}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable("deleteStatus") Integer deleteStatus) {
        return codeDeleteStatusService.delete(deleteStatus);
    }
}
