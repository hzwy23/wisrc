package com.wisrc.supplier.webapp.service;

import com.wisrc.supplier.webapp.utils.Result;

import java.util.List;
import java.util.Map;

public interface SupplierCommonService {

    // 根据编号获取供应商列表
    List<Map<String, Object>> getSuppliersById(String[] supplierId);

    // 根据条件模糊查询供应商帐号
    Result getSupplierAccount(String sId, Integer auditStatus, Integer type, String bank, String payee);

}