package com.wisrc.sys.webapp.service.impl;

import com.wisrc.sys.webapp.dao.SysRoleUserDao;
import com.wisrc.sys.webapp.entity.SysRoleUserEntity;
import com.wisrc.sys.webapp.service.SysRoleUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysRoleUserServiceImpl implements SysRoleUserService {


    private static final String ADMIN = "admin";

    @Autowired
    private SysRoleUserDao sysRoleUserDao;


    @Override
    public List<SysRoleUserEntity> findRoles(String userId) {
        return sysRoleUserDao.getSysRoleUserByUserId(userId);
    }

    @Override
    public boolean hasRole(String userId, String roleId) {
        return ADMIN.equals(userId) || sysRoleUserDao.checkAuth(userId, roleId) > 0;
    }
}
