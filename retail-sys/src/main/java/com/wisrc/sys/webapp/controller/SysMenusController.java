package com.wisrc.sys.webapp.controller;


import com.wisrc.sys.webapp.entity.SysResourceInfoEntity;
import com.wisrc.sys.webapp.service.MenusService;
import com.wisrc.sys.webapp.utils.Result;
import com.wisrc.sys.webapp.utils.ResultCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.regex.Pattern;

@RestController
@Api(tags = "资源管理")
@RequestMapping(value = "/sys")
public class SysMenusController {

    private final Logger logger = LoggerFactory.getLogger(SysMenusController.class);
    @Autowired
    private MenusService menusService;

    @RequestMapping(value = "/menus/main", method = RequestMethod.GET)
    @ApiOperation(value = "获取一级菜单信息", response = SysResourceInfoEntity.class)
    @ApiImplicitParam(value = "用户ID", name = "X-AUTH-ID", paramType = "header", dataType = "String", required = false)
    public Result getMainMenus(@RequestHeader("X-AUTH-ID") String userId) {
        // 根据用户ID，获取这个用户能够访问的菜单信息
        return menusService.getMainMenus(userId);
    }

    @RequestMapping(value = "/menus/routers", method = RequestMethod.GET)
    @ApiOperation(value = "获取路由信息", response = SysResourceInfoEntity.class)
    @ApiImplicitParam(value = "用户ID", name = "X-AUTH-ID", paramType = "header", dataType = "String", required = false)
    public Result getRouters(@RequestHeader("X-AUTH-ID") @ApiIgnore String userId,
                             @RequestParam("menuId") String menuId) {
        logger.info("get submenus, user id is : {}, menuId is : {}", userId, menuId);
        return menusService.getRouters(userId, menuId);
    }

    @RequestMapping(value = "/menus/details/{menuId}", method = RequestMethod.GET)
    @ApiImplicitParam(value = "用户ID", name = "X-AUTH-ID", paramType = "header", dataType = "String", required = false)
    public Result getMenuDetails(@RequestHeader("X-AUTH-ID") String userId, @PathVariable("menuId") String menuId) {
        logger.info(" menu id is: {}", menuId);
        return menusService.getMenuDetails(menuId);
    }

    @ApiOperation(value = "查询页面操作菜单", notes = "查询指定页面上操作菜单信息")
    @RequestMapping(value = "/menus/handle", method = RequestMethod.GET)
    public Result getPageHandle(@RequestParam("menuId") String menuId) {
        logger.info("user id is: {}, menu id is: {}", menuId);
        // todo 获取页面操作
        return menusService.getPageHandle(menuId);
    }

    @ApiOperation(value = "查询树形菜单", notes = "查询树形菜单，包含所有的菜单信息，不包含页面按钮信息")
    @RequestMapping(value = "/menus/tree", method = RequestMethod.GET)
    public Result getMenusTree(@RequestHeader(value = "X-AUTH-ID", required = false) String userId, @RequestParam(value = "isNode", required = true, defaultValue = "false") boolean isNode) {
        return menusService.getMenusTree(userId, isNode);
    }

    @ApiOperation(value = "查询菜单", notes = "查询用户可以访问到的所有菜单")
    @RequestMapping(value = "/menus", method = RequestMethod.GET)
    @ApiImplicitParam(value = "用户ID", name = "X-AUTH-ID", paramType = "header", dataType = "String", required = false)
    public Result getMenus(@RequestHeader("X-AUTH-ID") String userId) {
        // 查询用户能够访问的菜单信息
        return menusService.getMenus(userId);
    }

    @ApiOperation(value = "新增菜单", notes = "新增菜单")
    @RequestMapping(value = "/menus", method = RequestMethod.POST)
    public Result addMenus(@Validated SysResourceInfoEntity sysResourceInfoEntity, BindingResult br) {
        if (br.hasErrors()) {
            return Result.failure(390, br.getAllErrors().get(0).getDefaultMessage(), br.getAllErrors());
        }

        Result result = checkout(sysResourceInfoEntity);
        if (result.getCode() == 200) {
            try {
                return menusService.addMenu(sysResourceInfoEntity);
            } catch (DuplicateKeyException e) {
                return Result.failure(423, "菜单编码【" + sysResourceInfoEntity.getMenuId() + "】重复", sysResourceInfoEntity);
            }
        }
        return result;
    }

    @ApiOperation(value = "删除菜单", notes = "删除菜单，将会连同这个菜单下边的子菜单一起删除")
    @RequestMapping(value = "/menus/{menuId}", method = RequestMethod.DELETE)
    public Result deleteMenus(@PathVariable("menuId") String menuId) {
        logger.info("删除菜单信息, 菜单是：{}", menuId);
        if (menuId == null || menuId.isEmpty()) {
            return Result.failure(ResultCode.UNKNOW_ERROR, "菜单编码为空，无法删除");
        }
        return menusService.deleteMenu(menuId);
    }

    @ApiOperation(value = "编辑菜单", notes = "编辑菜单")
    @RequestMapping(value = "/menus", method = RequestMethod.PUT)
    public Result editMenus(@RequestBody SysResourceInfoEntity sysResourceInfoEntity) {
        Result result = checkout(sysResourceInfoEntity);
        if (result.getCode() == 200) {
            return menusService.updateMenu(sysResourceInfoEntity);
        }
        return result;
    }

    @ApiOperation(value = "查询菜单所有父级菜单", notes = "查询指定菜单的所有父级菜单")
    @RequestMapping(value = "/menus/parents/{menuId}", method = RequestMethod.GET)
    @ApiImplicitParam(value = "用户ID", name = "X-AUTH-ID", paramType = "header", dataType = "String", required = false)
    public Result getParents(@RequestHeader("X-AUTH-ID") String userId, @PathVariable("menuId") String menuId) {
        List<SysResourceInfoEntity> list = menusService.getParents(menuId);
        if (list == null) {
            return Result.failure(423, "【" + menuId + "】没有父级菜单", null);
        }
        return Result.success(list);
    }

    private Result checkout(SysResourceInfoEntity ele) {
        if (!Pattern.matches("^[0-9]*$", ele.getMenuId())) {
            return Result.failure(423, "菜单编码必须是数字", ele);
        }

        if (!Pattern.matches("^(-|)[0-9]*$", ele.getParentId())) {
            return Result.failure(423, "请选择上级菜单", ele);
        }

        if ("-1".equals(ele.getParentId()) && ele.getMenuType() != 1) {
            return Result.failure(423, "菜单类型非【系统模块】，禁止设置父级菜单编码为-1", ele);
        }

        if (ele.getMenuId().equals(ele.getParentId())) {
            return Result.failure(500, "菜单编码不能和父级菜单编码相同", "菜单编码与父级菜单编码相同，无效数据, 菜单编码是：" + ele.getMenuId() + "，父级菜单编码是：" + ele.getParentId());
        }

        return Result.success();
    }
}
