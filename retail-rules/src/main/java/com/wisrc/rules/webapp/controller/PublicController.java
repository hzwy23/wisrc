package com.wisrc.rules.webapp.controller;

import com.wisrc.rules.webapp.service.LogisticsRuleService;
import com.wisrc.rules.webapp.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/rules")
@RestController
@Api(value = "", tags = "调用控制层", description = "规则匹配")
public class PublicController {
    @Autowired
    private LogisticsRuleService logisticsRuleService;

    @ApiOperation(value = "规则状态选择项", notes = "")
    @RequestMapping(value = "/selector/status", method = RequestMethod.GET)
    public Result logisticsRuleStatus() {
        return logisticsRuleService.logisticsRuleStatus();
    }
}
