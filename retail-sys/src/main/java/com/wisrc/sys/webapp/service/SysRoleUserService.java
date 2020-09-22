package com.wisrc.sys.webapp.service;

import com.wisrc.sys.webapp.entity.SysRoleUserEntity;

import java.util.List;

public interface SysRoleUserService {
    List<SysRoleUserEntity> findRoles(String userId);

    boolean hasRole(String userId, String roleId);
}
