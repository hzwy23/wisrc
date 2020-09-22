package com.wisrc.sales.webapp.controller;

import com.wisrc.sales.webapp.service.externalService.SysManageService;
import com.wisrc.sales.webapp.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/sales")
public class SalesEmployeeController {
    @Autowired
    private SysManageService sysManageService;

    @RequestMapping(value = "/sale/current", method = RequestMethod.GET)
    public Result selectDetail() {

        try {
            Result result = sysManageService.getEmployeeId();
            return Result.success(result);
        } catch (Exception e) {
            return Result.success(500, e.getMessage(), "没有查到结果");
        }
    }
}
