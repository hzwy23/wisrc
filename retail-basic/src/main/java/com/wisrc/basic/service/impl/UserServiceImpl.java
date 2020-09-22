package com.wisrc.basic.service.impl;


import com.wisrc.basic.service.UserService;
import com.wisrc.basic.service.proxy.ExternalEmployeeService;
import com.wisrc.basic.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(value = "basicUserService")
public class UserServiceImpl implements UserService {
    @Autowired
    private ExternalEmployeeService externalUserService;

    @Override
    public Map getUserMap(List<String> userIdList) throws Exception {
        Map userMap = new HashMap();
        Result userResult = externalUserService.getUserBatch(userIdList);
        if (userResult.getCode() != 200) {
            throw new Exception("人员接口发生错误");
        }
        List<Map> userList = (List) userResult.getData();
        for (Map o : userList) {
            userMap.put(o.get("userId"), o.get("userName"));
        }
        return userMap;
    }
}
