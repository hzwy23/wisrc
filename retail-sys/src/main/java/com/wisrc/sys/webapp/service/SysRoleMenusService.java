package com.wisrc.sys.webapp.service;

import com.wisrc.sys.webapp.entity.SysRoleMenusEntity;

import java.util.List;

public interface SysRoleMenusService {
    List<SysRoleMenusEntity> findById(String userId, String roleId);
}
