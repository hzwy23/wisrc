package com.wisrc.warehouse.webapp.service.impl;

import com.wisrc.warehouse.webapp.service.BlitemRemarkService;
import com.wisrc.warehouse.webapp.dao.BlitemRemarkDao;
import com.wisrc.warehouse.webapp.entity.BlitemRemarkEntity;
import com.wisrc.warehouse.webapp.service.externalService.EmployeeService;
import com.wisrc.warehouse.webapp.utils.Result;
import com.wisrc.warehouse.webapp.utils.Time;
import com.wisrc.warehouse.webapp.utils.UUIDutil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class BlitemRemarkServiceImpl implements BlitemRemarkService {

    @Autowired
    private BlitemRemarkDao remarkDao;

    @Autowired
    private EmployeeService employeeService;

    @Override
    public void save(String blitemId, String remark, String userId) {
        //获取当前的用户信息
        List<String> useridList = new ArrayList<>();
        useridList.add(userId);
        Result employee = employeeService.getEmployee(useridList);
        List<Object> employees = (List<Object>) employee.getData();
        Map employyeMap = (Map) employees.get(0);
        String employeeId = (String) employyeMap.get("employeeId");
        BlitemRemarkEntity remarkEntity = new BlitemRemarkEntity();
        remarkEntity.setUuid(UUIDutil.randomUUID());
        remarkEntity.setBlitemId(blitemId);
        remarkEntity.setRemarkContent(remark);
        remarkEntity.setRemarkUser(employeeId);
        remarkEntity.setRemarkTime(Time.getCurrentTime());
        remarkDao.add(remarkEntity);
    }
}
