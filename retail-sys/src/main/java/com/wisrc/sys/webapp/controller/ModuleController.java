package com.wisrc.sys.webapp.controller;

import com.wisrc.sys.webapp.service.ModuleService;
import com.wisrc.sys.webapp.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "模块列表信息")
@RequestMapping(value = "/sys")
public class ModuleController {
    private final Logger logger = LoggerFactory.getLogger(ModuleController.class);

    @Autowired
    private ModuleService moduleService;

    @RequestMapping(value = "/module", method = RequestMethod.GET)
    @ApiOperation(value = "通过角色编码查找哪些拥有一级模块列表信息")
    @ApiImplicitParam(name = "roleId", value = "角色Id", paramType = "query", dataType = "String", required = true)
    public Result module(@RequestParam("roleId") String roleId) {
        return moduleService.getModuleByRoleId(roleId);
    }

}
