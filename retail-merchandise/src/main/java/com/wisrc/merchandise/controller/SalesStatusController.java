package com.wisrc.merchandise.controller;

import com.wisrc.merchandise.service.SalesStatusService;
import com.wisrc.merchandise.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/operation/sales/status")
@RestController
@Api(tags = "销售状态")
public class SalesStatusController {
    @Autowired
    private SalesStatusService salesStatusService;

    @GetMapping("/selector")
    @ApiOperation(value = "查询销售状态选择框", notes = "")
    public Result getSelector() {
        return salesStatusService.getSelector();
    }
}
