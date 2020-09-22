package com.wisrc.sys.webapp.controller;


import com.wisrc.sys.webapp.vo.authorize.AuthorizeUserPrivilegeVo;
import com.wisrc.sys.webapp.vo.authorize.AuthorizeUserRoleVo;
import com.wisrc.sys.webapp.service.AuthorizeService;
import com.wisrc.sys.webapp.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/sys/authorize")
@Api(tags = "授权管理")
public class AuthorizeController {

    @Autowired
    private AuthorizeService authorizeService;

    @RequestMapping(value = "/{account}/roles/edit", method = RequestMethod.PUT)
    @ApiOperation(value = "编辑账号授权角色信息")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(value = "用户ID", name = "X-AUTH-ID", paramType = "header", dataType = "String", required = true, defaultValue = ""),
            @ApiImplicitParam(value = "账号ID", name = "account", paramType = "path", dataType = "String", required = true)
    })

    public Result editUserRoles(@Valid @RequestBody AuthorizeUserRoleVo authorizeUserRoleVo,
                                @PathVariable("account") String account,
                                @RequestHeader("X-AUTH-ID") String userId,
                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            return Result.failure(400, errorList.get(0).getDefaultMessage(), authorizeUserRoleVo);
        }
        return authorizeService.editRoleUser(authorizeUserRoleVo, account, userId);
    }

    @RequestMapping(value = "/{roleId}/privilege/edit", method = RequestMethod.PUT)
    @ApiOperation(value = "编辑账号授权数据权限信息")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(value = "角色ID", name = "roleId", paramType = "path", dataType = "String", required = true)
    })

    public Result editUserRoles(@Valid @RequestBody AuthorizeUserPrivilegeVo authorizeUserPrivilegeVo,
                                @PathVariable("roleId") String roleId,
                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            return Result.failure(400, errorList.get(0).getDefaultMessage(), authorizeUserPrivilegeVo);
        }
        return authorizeService.editRolePrivilege(authorizeUserPrivilegeVo, roleId);
    }

    @RequestMapping(value = "/{roleId}/privilege", method = RequestMethod.GET)
    @ApiOperation(value = "获取角色授权数据权限信息")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(value = "账号ID", name = "roleId", paramType = "path", dataType = "String", required = true)
    })

    public Result getUserRoles(@PathVariable("roleId") String roleId) {
        return authorizeService.getRolePrivilege(roleId);
    }
}
