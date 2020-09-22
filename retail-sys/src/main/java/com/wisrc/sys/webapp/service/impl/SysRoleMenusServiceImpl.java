package com.wisrc.sys.webapp.service.impl;

import com.wisrc.sys.webapp.dao.SysRoleMenusDao;
import com.wisrc.sys.webapp.entity.SysRoleMenusEntity;
import com.wisrc.sys.webapp.service.SysRoleMenusService;
import com.wisrc.sys.webapp.service.SysRoleUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysRoleMenusServiceImpl implements SysRoleMenusService {

    @Autowired
    private SysRoleMenusDao sysRoleMenusDao;

    @Autowired
    private SysRoleUserService sysRoleUserService;

    @Override
    public List<SysRoleMenusEntity> findById(String userId, String roleId) {
        // 判断用户是否有有读取这个角色的权限
        if (sysRoleUserService.hasRole(userId, roleId)) {
            return sysRoleMenusDao.findByRoleId(roleId);
        }
        return null;
    }
}
