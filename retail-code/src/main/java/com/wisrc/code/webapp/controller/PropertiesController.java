package com.wisrc.code.webapp.controller;

import com.wisrc.code.webapp.service.PropertiesService;
import com.wisrc.code.webapp.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/code/properties")
@Api(value = "/code/properties", tags = "固定参数信息")
public class PropertiesController {
    @Autowired
    private PropertiesService propertiesService;

    @ApiOperation(value = "获取固定参数信息", notes = "")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Result getKey(@RequestParam("keys") List keys) {
        return propertiesService.getKey(keys);
    }
}
