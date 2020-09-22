package com.wisrc.sys.webapp.service;

import com.wisrc.sys.webapp.vo.authorize.AuthorizeUserPrivilegeVo;
import com.wisrc.sys.webapp.vo.authorize.AuthorizeUserRoleVo;
import com.wisrc.sys.webapp.utils.Result;

public interface AuthorizeService {
    Result editRoleUser(AuthorizeUserRoleVo authorizeUserRoleVo, String account, String userId);

    Result editRolePrivilege(AuthorizeUserPrivilegeVo authorizeUserPrivilegeVo, String roleId);

    Result getRolePrivilege(String roleId);
}
