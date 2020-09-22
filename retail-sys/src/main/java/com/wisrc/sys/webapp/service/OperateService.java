package com.wisrc.sys.webapp.service;

import com.wisrc.sys.webapp.entity.VSysMenuUserEntity;
import com.wisrc.sys.webapp.utils.Result;

import java.util.List;

public interface OperateService {
    Result getOperateList(String userId, String roleId, String menuId);

    /**
     * @param userId   账号ID
     * @param menuId   菜单ID
     * @param methodCd 请求方式
     * @param path     路由地址
     * @param menuType 菜单类型
     */
    List<VSysMenuUserEntity> getOperationAuth(String userId,
                                              String menuId,
                                              Integer methodCd,
                                              String path,
                                              Integer menuType);
}
