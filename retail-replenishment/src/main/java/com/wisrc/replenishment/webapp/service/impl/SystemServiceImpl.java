package com.wisrc.replenishment.webapp.service.impl;

import com.wisrc.replenishment.webapp.service.SystemOutsideService;
import com.wisrc.replenishment.webapp.service.SystemService;
import com.wisrc.replenishment.webapp.utils.Result;
import com.wisrc.replenishment.webapp.utils.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SystemServiceImpl implements SystemService {
    @Autowired
    private SystemOutsideService systemOutsideService;

    public Result getEmployySelector() {
        Result employeeResult;
        List result = new ArrayList();

        try {
            employeeResult = systemOutsideService.getEmployeePage("", 1, 99999);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(400, "", "人员接口发生错误");
        }

        if (employeeResult.getCode() != 200) {
            return employeeResult;
        }

        List<Map> employeeList = (List<Map>) ServiceUtils.LinkedHashMapToMap(employeeResult.getData()).get("employeeInfoList");
        for (Map employee : employeeList) {
            Map selector = ServiceUtils.idAndName(employee.get("employeeId"), String.valueOf(employee.get("employeeName")));
            result.add(selector);
        }

        return Result.success(result);
    }
}
