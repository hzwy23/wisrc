package com.wisrc.code.webapp.controller;

import com.wisrc.code.webapp.vo.codeTemplateConf.CodeTemplateConfSaveVo;
import com.wisrc.code.webapp.service.CodeTemplateConfService;
import com.wisrc.code.webapp.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/code/upload")
@Api(value = "/code/upload", tags = "上传文件信息")
public class CodeTemplateConfController {
    @Autowired
    private CodeTemplateConfService codeTemplateConfService;

    @ApiOperation(value = "获取所有上传文件", notes = "")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Result getCodeTemplateConf() {
        return codeTemplateConfService.getCodeTemplateConf();
    }

    @ApiOperation(value = "保存上传文件信息", notes = "")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result saveCodeTemplateConf(@RequestBody CodeTemplateConfSaveVo codeTemplateConfSaveVo) {
        return codeTemplateConfService.saveCodeTemplateConf(codeTemplateConfSaveVo);
    }

    @ApiOperation(value = "获取上传文件信息", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "itemId", value = "文件id", paramType = "path", dataType = "string")
    })
    @RequestMapping(value = "/file/{itemId}", method = RequestMethod.GET)
    public Result getCodeTemplateConfById(@PathVariable("itemId") String itemId) {
        return codeTemplateConfService.getCodeTemplateConfById(itemId);
    }
}
