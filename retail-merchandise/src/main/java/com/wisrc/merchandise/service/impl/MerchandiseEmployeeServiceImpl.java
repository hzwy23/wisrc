package com.wisrc.merchandise.service.impl;


import com.wisrc.merchandise.dto.DeptOperationEmployeeDTO;
import com.wisrc.merchandise.service.EmployeeOutsideService;
import com.wisrc.merchandise.service.EmployeeService;
import com.wisrc.merchandise.utils.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MerchandiseEmployeeServiceImpl implements EmployeeService {
    private final Logger logger = LoggerFactory.getLogger(MerchandiseEmployeeServiceImpl.class);
    @Autowired
    private EmployeeOutsideService employeeOutsideService;

    @Override
    public Map getEmployeeNameMap(List<String> employeeIds) {
        Map employeeResult = new HashMap();

        Result employeeNameResult = employeeOutsideService.getEmployeeNameBatch(employeeIds);
        if (employeeNameResult.getCode() != 200) {
            return employeeResult;
        }
        List<Map> employeeList = (List) employeeNameResult.getData();
        for (Map employee : employeeList) {
            employeeResult.put(employee.get("employeeId"), employee.get("employeeName"));
        }

        return employeeResult;
    }

    @Override
    public List<LinkedHashMap> employeeSelector(String deptCd) {
        try {
            Result employeeResult = employeeOutsideService.getEmployeeSelector(deptCd);
            if (employeeResult.getCode() == 200) {
                return (List<LinkedHashMap>) employeeResult.getData();
            }
            logger.error(employeeResult.toString());
            return null;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    public HashMap<String, DeptOperationEmployeeDTO> getEmployees(List<String> ids) {

        String[] idsList = new String[ids.size()];
        for (int i = 0; i < ids.size(); i++) {
            idsList[i] = ids.get(i);
        }

        HashMap<String, DeptOperationEmployeeDTO> map = new HashMap<String, DeptOperationEmployeeDTO>();
        Result result = employeeOutsideService.getEmployeeAll(idsList);
        if (result.getCode() == 200) {
            List<HashMap<String, String>> list = (List<HashMap<String, String>>) result.getData();
            for (HashMap<String, String> ele : list) {
                DeptOperationEmployeeDTO dto = new DeptOperationEmployeeDTO();
                dto.setDeptCd(ele.get("deptCd"));
                dto.setDeptName(ele.get("deptName"));
                dto.setEmployeeId(ele.get("employeeId"));
                dto.setEmployeeName(ele.get("employeeName"));
                dto.setPositionCd(ele.get("positionCd"));
                dto.setPositionName(ele.get("positionName"));
                map.put(dto.getEmployeeId(), dto);
            }
        }
        return map;
    }

    @Override
    public List<String> mskuPrivilege(String userId) throws Exception {
        logger.info("用户账号是：{}。", userId);
        List mskuList = new ArrayList();
        Result peivilege = employeeOutsideService.getPrivilege(userId);

        if (peivilege == null || peivilege.getCode() != 200) {
            throw new Exception(peivilege.getMsg());
        }

        List<String> peivilegeData = (List) peivilege.getData();
        for (String peivilegeMap : peivilegeData) {
            mskuList.add(peivilegeMap);
        }

        return mskuList;
    }
}
