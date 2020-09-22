package com.wisrc.supplier.webapp.service;

import com.wisrc.supplier.webapp.entity.Supplier;

import java.util.Map;

public interface SupplierManageService {

    // 供应商 列表
    Map<String, Object> getSuppliers(String supplierId, String supplierName, String startTime, String endTime, Integer status, Integer currentPage, Integer pageSize);

    // 供应商 详情
    Map<String, Object> getSupplierInfo(String sId);

    // 供应商信息 添加
    Map<String, Object> addSupplierInfo(Supplier supplier);

    // 供应商信息 更新
    Map<String, Object> setSupplierInfo(Supplier supplier);

    // 供应商状态 更新
    Map<String, Object> setSupplierStatus(Integer status, String[] supplierId);
}
