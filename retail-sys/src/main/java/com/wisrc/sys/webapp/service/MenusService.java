package com.wisrc.sys.webapp.service;

import com.wisrc.sys.webapp.entity.SysResourceInfoEntity;
import com.wisrc.sys.webapp.utils.Result;

import java.util.List;


public interface MenusService {
    Result getMenus(String userId);

    List<SysResourceInfoEntity> getParents(String menuId);

    /**
     * 查询用户被授权了的主菜单信息
     *
     * @param userId 已登陆用户ID
     */
    Result getMainMenus(String userId);

    Result getRouters(String userId, String menuId);

    Result getMenuDetails(String menuId);

    Result getMenusTree(String userId, boolean isNode);

    Result getPageHandle(String menuId);

    Result addMenu(SysResourceInfoEntity sysResourceInfoEntity);

    Result deleteMenu(String menuId);

    Result updateMenu(SysResourceInfoEntity sysResourceInfoEntity);
}
