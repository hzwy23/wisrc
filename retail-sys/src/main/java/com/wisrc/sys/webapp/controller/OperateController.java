package com.wisrc.sys.webapp.controller;

import com.wisrc.sys.webapp.entity.VSysMenuUserEntity;
import com.wisrc.sys.webapp.service.OperateService;
import com.wisrc.sys.webapp.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = "操作权限信息")
@RequestMapping(value = "/sys")
public class OperateController {

    private final Logger logger = LoggerFactory.getLogger(OperateController.class);

    @Autowired
    private OperateService operateService;

    @RequestMapping(value = "/operate", method = RequestMethod.GET)
    @ApiOperation(value = "通过角色编码查找2级模块菜单下的操作菜单")
    @ApiImplicitParam(name = "roleId", value = "角色Id", paramType = "query", dataType = "String", required = false)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色编码", paramType = "query", dataType = "String", required = true),
            @ApiImplicitParam(name = "menuId", value = "菜单id", paramType = "query", dataType = "String", required = true),
            @ApiImplicitParam(value = "用户ID", name = "X-AUTH-ID", paramType = "header", dataType = "String", required = true)
    })
    public Result operate(@RequestHeader("X-AUTH-ID") String userId,
                          @RequestParam("roleId") String roleId,
                          @RequestParam("menuId") String menuId) {
        return operateService.getOperateList(userId, roleId, menuId);
    }


    @RequestMapping(value = "/valid/operation", method = RequestMethod.GET)
    public Result getAuth(@RequestHeader(value = "X-AUTH-ID") String userId,
                          @RequestParam(value = "menuId", required = false) String menuId,
                          @RequestParam(value = "path", required = false) String path,
                          @RequestParam(value = "methodCd", required = false) Integer methodCd,
                          @RequestParam(value = "menuType", required = false) Integer menuType) {
        if ("admin".equals(userId)) {
            return Result.success("超级管理员");
        }

        try {
            List<VSysMenuUserEntity> authList = operateService.getOperationAuth(userId, menuId, methodCd, path, menuType);
            if (authList != null && authList.size() > 0) {
                return Result.success(authList);
            }
            return Result.failure(401, "权限不足，请联系管理员授权", path);
        } catch (Exception e) {
            return Result.failure(401, "权限不足", path);
        }
    }
}
