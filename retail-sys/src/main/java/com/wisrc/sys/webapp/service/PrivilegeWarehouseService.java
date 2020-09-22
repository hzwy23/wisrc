package com.wisrc.sys.webapp.service;

import com.wisrc.sys.webapp.entity.SysPrivilegeWarehouseEntity;
import com.wisrc.sys.webapp.utils.Result;

import java.util.LinkedHashMap;
import java.util.List;

public interface PrivilegeWarehouseService {
    Result insert(List<SysPrivilegeWarehouseEntity> list);

    Result deletePrivilegeWarehouse(SysPrivilegeWarehouseEntity entity);

    LinkedHashMap getPrivilegeWarehouse(int pageNum, int pageSize, String authId, String type, String keyWord);

    LinkedHashMap getPrivilegeWarehouseUnauth(int pageNum, int pageSize, String authId, String type, String keyWord);
}
