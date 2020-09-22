package com.wisrc.code.webapp.service.impl;

import com.wisrc.code.webapp.service.proxy.ExternalEmployeeService;
import com.wisrc.code.webapp.service.EmployeeService;
import com.wisrc.code.webapp.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private ExternalEmployeeService externalEmployeeService;

    @Override
    public Map getEmployeeNameMap(List employeeIds) throws Exception {
        Map employeeResult = new HashMap();

        Result employeeNameResult = externalEmployeeService.getEmployeeNameBatch(employeeIds);
        if (employeeNameResult.getCode() != 200) {
            throw new Exception("人员接口发生错误");
        }
        List<Map> employeeList = (List) employeeNameResult.getData();
        for (Map employee : employeeList) {
            employeeResult.put(employee.get("employeeId"), employee.get("employeeName"));
        }
        return employeeResult;
    }
}
