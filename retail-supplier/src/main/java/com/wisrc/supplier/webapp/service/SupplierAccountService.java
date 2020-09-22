package com.wisrc.supplier.webapp.service;

import com.wisrc.supplier.webapp.entity.SupplierAccount;
import com.wisrc.supplier.webapp.utils.Result;

import java.util.Map;

public interface SupplierAccountService {

    // 供应商帐号 列表
    Map<String, Object> getSupplierAccounts(String sId, String supplierName, Integer type, Integer pageSize, Integer currentPage);

    // 供应商帐号 添加
    boolean addSupplierAccount(SupplierAccount account);

    // 供应商帐号 更新
    boolean setSupplierAccount(SupplierAccount account);

    // 供应商帐号 审核
    Result verifySupplierAccount(Integer id, Integer auditStatus, String auditInfo);

    // 供应商帐号 删除
    Map<String, Object> delSupplierAccount(Integer id);

}
