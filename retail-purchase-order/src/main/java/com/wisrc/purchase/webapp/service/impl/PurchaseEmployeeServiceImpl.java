package com.wisrc.purchase.webapp.service.impl;

import com.wisrc.purchase.webapp.service.EmployeeOutsideService;
import com.wisrc.purchase.webapp.service.PurchaseEmployeeService;
import com.wisrc.purchase.webapp.utils.Result;
import com.wisrc.purchase.webapp.vo.inspection.EmployeeSelectorVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PurchaseEmployeeServiceImpl implements PurchaseEmployeeService {
    @Autowired
    private EmployeeOutsideService employeeOutsideService;

    @Override
    public Map getEmployeeNameMap(List employeeIds) throws Exception {
        Map employeeResult = new HashMap();

        String[] emps = new String[employeeIds.size()];

        for (int i = 0; i < emps.length; i++) {
            emps[i] = (String) employeeIds.get(i);
        }
        Result employeeNameResult = employeeOutsideService.getEmployeeNameBatch(emps);

        if (employeeNameResult.getCode() != 200) {
            throw new Exception(employeeNameResult.getMsg());
        }
        List<Map> employeeList = (List) employeeNameResult.getData();
        for (Map employee : employeeList) {
            employeeResult.put(employee.get("employeeId"), employee.get("employeeName"));
        }

        return employeeResult;
    }

    @Override
    public Result getEmployeeSelector(EmployeeSelectorVo employeeSelectorVo) {
        try {
            Result result = employeeOutsideService.getEmployeeSelector(employeeSelectorVo.getEmployeeName(), employeeSelectorVo.getPageNum(), employeeSelectorVo.getPageSize(), "");
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }
    }

    @Override
    public Map getUserBatch(List useridList) throws Exception {
        Map result = new HashMap();

        Result userResult = employeeOutsideService.getUserBatch(useridList);
        if (userResult.getCode() != 200) {
            throw new Exception(userResult.getMsg());
        }

        List<Map> userList = (List) userResult.getData();
        for (Map userMap : userList) {
            result.put(userMap.get("userId"), userMap.get("userName"));
        }

        return result;
    }
}
