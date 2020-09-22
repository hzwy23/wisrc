package com.wisrc.sys.webapp.service;

import com.wisrc.sys.webapp.vo.role.RoleAuthorizeVo;
import com.wisrc.sys.webapp.vo.role.RoleInfoPageVo;
import com.wisrc.sys.webapp.vo.role.RoleInfoVo;
import com.wisrc.sys.webapp.vo.role.RoleSwitchVo;
import com.wisrc.sys.webapp.utils.Result;
import com.wisrc.sys.webapp.vo.SysRoleUserVO;
import org.springframework.validation.BindingResult;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

public interface RoleService {

    LinkedHashMap<String, List<SysRoleUserVO>> findRolesByUserIdList(Set<String> idList);

    Result getRoles(RoleInfoPageVo roleInfoPageVo, BindingResult bindingResult);

    Result getRoleById(String roleId);

    Result saveRole(RoleInfoVo roleInfoVo, String userId, String roleId, BindingResult bindingResult);

    Result editRole(RoleInfoVo roleInfoVo, String roleId, BindingResult bindingResult);

    Result deleteRole(String roleId);

    Result getNewRoleId();

    Result banAllRole(List roleIds);

    Result roleSwitch(String roleId, RoleSwitchVo roleSwitchVo, BindingResult bindingResult);

    /**
     * 给角色添加菜单权限或删除菜单权限
     *
     * @param roleId          角色编码值
     * @param roleAuthorizeVo 里边包含需要删除的菜单和添加的菜单
     */
    Result roleAuthorize(String roleId, RoleAuthorizeVo roleAuthorizeVo);

}
