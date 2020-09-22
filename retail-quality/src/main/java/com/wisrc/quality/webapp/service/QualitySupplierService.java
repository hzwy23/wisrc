package com.wisrc.quality.webapp.service;

import com.wisrc.quality.webapp.utils.Result;

import java.util.List;
import java.util.Map;

public interface QualitySupplierService {
    Map getSupplierNameMap(List<String> productIds) throws Exception;

    List getFindKeyDealted(String findKey) throws Exception;

    Result getSupplierInfo(String supplierId);
}
