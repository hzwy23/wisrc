package com.wisrc.warehouse.webapp.controller;

import com.wisrc.warehouse.webapp.service.BlitemStatusService;
import com.wisrc.warehouse.webapp.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "盘点单状态")
@RequestMapping(value = "/warehouse")
public class BlitemStatusController {
    @Autowired
    private BlitemStatusService statusService;


    @RequestMapping(value = "/blitem/status/list", method = RequestMethod.POST)
    @ApiOperation("获取所有盘点单状态")
    public Result list() {
        try {
            Result result = statusService.findAll();
            return result;
        } catch (Exception e) {
            return Result.failure(390, e.getMessage(), null);
        }
    }

    @RequestMapping(value = "/blitem/status/{statusCd}", method = RequestMethod.GET)
    @ApiOperation("获取状态码对应的状态")
    public Result get(@PathVariable("statusCd") String statusCd) {
        try {
            Result result = statusService.findById(statusCd);
            return result;
        } catch (Exception e) {
            return Result.failure(390, e.getMessage(), null);
        }
    }


}
