package com.wisrc.sys.webapp.service;

import com.wisrc.sys.webapp.utils.Result;

public interface WarehouseService {
    Result getPrivilegeWarehouse(int pageNum, int pageSize, String authId);
}
