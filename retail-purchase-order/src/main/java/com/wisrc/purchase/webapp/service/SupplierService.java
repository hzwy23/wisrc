package com.wisrc.purchase.webapp.service;

import com.wisrc.purchase.webapp.utils.Result;

import java.util.List;
import java.util.Map;

public interface SupplierService {
    Map getSupplierNameMap(List<String> productIds) throws Exception;

    List getFindKeyDealted(String findKey) throws Exception;

    Result getSupplierInfo(String supplierId);

    List getList(Map supplierResult) throws Exception;
}
