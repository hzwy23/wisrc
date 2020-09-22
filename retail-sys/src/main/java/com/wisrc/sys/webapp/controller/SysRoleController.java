package com.wisrc.sys.webapp.controller;


import com.wisrc.sys.webapp.vo.role.*;
import com.wisrc.sys.webapp.entity.SysRoleMenusEntity;
import com.wisrc.sys.webapp.service.RoleService;
import com.wisrc.sys.webapp.service.SysRoleMenusService;
import com.wisrc.sys.webapp.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/sys/role")
@Api(tags = "角色管理")
public class SysRoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private SysRoleMenusService sysRoleMenusService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ApiOperation(value = "查询角色信息", notes = "")
    public Result roleList(@Valid RoleInfoPageVo roleInfoPageVo, BindingResult bindingResult) {
        return roleService.getRoles(roleInfoPageVo, bindingResult);
    }

    @RequestMapping(value = "/{roleId}", method = RequestMethod.GET)
    @ApiOperation(value = "获取角色内容", response = RoleInfoVo.class)
    @ApiImplicitParam(value = "角色ID", name = "roleId", paramType = "path", dataType = "String", required = true)
    public Result getRole(@PathVariable("roleId") String roleId) {
        return roleService.getRoleById(roleId);
    }

    @RequestMapping(value = "/{roleId}", method = RequestMethod.POST)
    @ApiOperation(value = "新增角色信息")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(value = "账号ID", name = "X-AUTH-ID", paramType = "header", dataType = "String", required = true)
    })

    public Result saveRole(@Valid @RequestBody RoleInfoVo roleInfoVo, @RequestHeader("X-AUTH-ID") String userId, @PathVariable("roleId") String roleId, BindingResult bindingResult) {
        return roleService.saveRole(roleInfoVo, userId, roleId, bindingResult);
    }

    @RequestMapping(value = "/{roleId}", method = RequestMethod.PUT)
    @ApiOperation(value = "编辑角色信息")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(value = "角色Id", name = "roleId", paramType = "path", dataType = "String", required = true),
    })
    public Result editRole(@PathVariable("roleId") String roleId, @Valid @RequestBody RoleInfoEditVo roleInfoVo, BindingResult bindingResult) {
        return roleService.editRole(roleInfoVo, roleId, bindingResult);
    }

    @RequestMapping(value = "/{roleId}/forbidden", method = RequestMethod.PUT)
    @ApiOperation(value = "启用禁用角色")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(value = "角色Id", name = "roleId", paramType = "path", dataType = "String", required = true)
    })
    public Result roleSwitch(@Valid @RequestBody RoleSwitchVo roleSwitchVo, @PathVariable("roleId") String roleId, BindingResult bindingResult) {
        return roleService.roleSwitch(roleId, roleSwitchVo, bindingResult);
    }


    @RequestMapping(value = "/{roleId}", method = RequestMethod.DELETE)
    @ApiOperation(value = "删除角色信息")
    @ApiImplicitParam(value = "角色Id", name = "roleId", paramType = "path", dataType = "String", required = true)
    public Result deleteRole(@PathVariable("roleId") String roleId) {
        return roleService.deleteRole(roleId);
    }

    @RequestMapping(value = "/id/new", method = RequestMethod.GET)
    @ApiOperation(value = "获取角色新Id")
    public Result getNewRoleId() {
        return roleService.getNewRoleId();
    }

    @RequestMapping(value = "/ban", method = RequestMethod.DELETE)
    @ApiOperation(value = "批量禁用角色信息")
    @ApiImplicitParam(value = "角色Id数组", name = "roleIds", paramType = "query", dataType = "List", required = true)
    public Result banRoleBatch(@RequestParam("roleIds") List roleIds) {
        return roleService.banAllRole(roleIds);
    }

    @RequestMapping(value = "/{roleId}/authorize", method = RequestMethod.PUT)
    @ApiOperation(value = "编辑角色菜单权限")
    public Result editRoleAuthorize(@PathVariable("roleId") String roleId,
                                    @RequestBody RoleAuthorizeVo roleAuthorizeVo) {
        return roleService.roleAuthorize(roleId, roleAuthorizeVo);
    }


    @ApiImplicitParams({
            @ApiImplicitParam(value = "角色编码", name = "roleId", dataType = "string", paramType = "query", required = true)
    })
    @ApiOperation(value = "获取角色拥有的菜单资源", notes = "根据角色ID，获取这个角色拥有的菜单资源信息")
    @RequestMapping(value = "/menus", method = RequestMethod.GET)
    public Result getMenusByRoleId(@RequestHeader(value = "X-AUTH-ID", required = false) String userId, @RequestParam("roleId") String roleId) {
        // 校验用户是否有权限访问这个角色
        List<SysRoleMenusEntity> result = sysRoleMenusService.findById(userId, roleId);
        if (result == null) {
            return Result.failure(423, "没有权限访问这个角色", roleId);
        }
        return Result.success(result);
    }
}
