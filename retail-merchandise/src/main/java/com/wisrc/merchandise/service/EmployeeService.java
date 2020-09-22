package com.wisrc.merchandise.service;


import com.wisrc.merchandise.dto.DeptOperationEmployeeDTO;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface EmployeeService {
    Map getEmployeeNameMap(List<String> employeeIds);

    List<LinkedHashMap> employeeSelector(String name);

    HashMap<String, DeptOperationEmployeeDTO> getEmployees(List<String> ids);

    List<String> mskuPrivilege(String userId) throws Exception;
}
