package com.wisrc.purchase.webapp.service;

import com.wisrc.purchase.webapp.utils.Result;
import com.wisrc.purchase.webapp.vo.inspection.EmployeeSelectorVo;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

public interface PurchaseEmployeeService {
    Map getEmployeeNameMap(List employeeIds) throws Exception;

    Result getEmployeeSelector(EmployeeSelectorVo employeeSelectorVo);

    Map getUserBatch(@RequestParam("useridList") List<String> useridList) throws Exception;
}
