package com.wisrc.merchandise.controller;//package com.wisrc.merchandise.controller;


import com.wisrc.merchandise.service.EmployeeService;
import com.wisrc.merchandise.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/operation/employee")
@Controller
@Api(tags = "员工信息")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/selector")
    @ApiOperation(value = "查询人员信息", notes = "")
    @ApiImplicitParam(paramType = "query", dataType = "String", name = "teamCd", value = "小组名称", required = true)
    @ResponseBody
    public Result employeeSelector(@RequestParam("teamCd") String teamCd) {
        Map result = new HashMap<>();
        result.put("manager", employeeService.employeeSelector(teamCd));
        return new Result(200, "查询团队成员成功", result);
    }
}
