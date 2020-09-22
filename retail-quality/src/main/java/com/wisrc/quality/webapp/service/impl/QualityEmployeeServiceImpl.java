package com.wisrc.quality.webapp.service.impl;

import com.wisrc.quality.webapp.service.EmployeeOutsideService;
import com.wisrc.quality.webapp.service.QualityEmployeeService;
import com.wisrc.quality.webapp.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class QualityEmployeeServiceImpl implements QualityEmployeeService {
    @Autowired
    private EmployeeOutsideService employeeOutsideService;

    @Override
    public Map getEmployeeNameMap(List employeeIds) throws Exception {
        Map employeeResult = new HashMap();
//
//        Map batchEmployeeId = new HashMap();
//        batchEmployeeId.put("employeeIdList", employeeIds);
        String[] batchEmployeeId = new String[employeeIds.size()];
        Result employeeNameResult = employeeOutsideService.getEmployeeNameBatch((String[]) employeeIds.toArray(batchEmployeeId));
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
