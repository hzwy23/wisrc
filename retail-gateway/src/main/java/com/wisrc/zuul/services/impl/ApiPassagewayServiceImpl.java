package com.wisrc.zuul.services.impl;

import com.wisrc.zuul.dao.UserMenusDao;
import com.wisrc.zuul.entity.UserMenusEntity;
import com.wisrc.zuul.services.ApiPassagewayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ApiPassagewayServiceImpl implements ApiPassagewayService {

    private static final String ADMIN = "admin";
    private static final Map<String, Integer> HTTP_METHODS = new HashMap<String, Integer>() {{
        put("GET", 1);
        put("POST", 2);
        put("PUT", 3);
        put("DELETE", 4);
        put("OPTIONS", 5);
    }};
    @Autowired
    private UserMenusDao userMenusDao;

    @Override
    public boolean pass(String userId, String path, String methodCd) {
        if (ADMIN.equals(userId)) {
            return true;
        }
        UserMenusEntity ume = new UserMenusEntity();
        ume.setUserId(userId);
        ume.setPath(path);
        ume.setMethodCd(HTTP_METHODS.get(methodCd));
        // 获取需要进行权限校验的接口
        int isAuth = userMenusDao.isAuth(ume);

        // 如果请求的路径和方法不在权限控制的范围之内，则默认有权限
        if (isAuth == 0) {
            return true;
        } else {
            // 检查用户是否拥有访问这个API的权限
            int cnt = userMenusDao.checkApiAuth(ume);
            return cnt > 0;
        }
    }
}
