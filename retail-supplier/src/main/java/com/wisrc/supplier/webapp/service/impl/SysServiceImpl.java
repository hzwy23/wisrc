package com.wisrc.supplier.webapp.service.impl;

import com.wisrc.supplier.webapp.service.proxy.SysService;
import com.wisrc.supplier.webapp.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SysServiceImpl {

    @Autowired
    private SysService sysService;

    @SuppressWarnings("unchecked")
    public Object getUserInfo(String userId) {
        try {
            if (userId != null) {
                Result userRes = sysService.getUserInfo(userId);
                if (userRes.getCode() == 200) {
                    return ((Map<String, Object>) userRes.getData()).get("userName");
                }
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

}
