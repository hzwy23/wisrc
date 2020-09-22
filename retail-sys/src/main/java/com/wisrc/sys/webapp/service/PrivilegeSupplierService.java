package com.wisrc.sys.webapp.service;

import com.wisrc.sys.webapp.entity.SysPrivilegeSupplierEntity;
import com.wisrc.sys.webapp.utils.Result;

import java.util.List;

public interface PrivilegeSupplierService {
    Result getPrivilegeSupplier(int pageNum, int pageSize, String authId, String supplierId, String supplierName);

    Result insert(List<SysPrivilegeSupplierEntity> entity);

    Result getPrivilegeShopUnauth(int pageNum, int pageSize, String authId, String supplierId, String supplierName);

    Result deletePrivilegeSupplierId(SysPrivilegeSupplierEntity entity);
}
