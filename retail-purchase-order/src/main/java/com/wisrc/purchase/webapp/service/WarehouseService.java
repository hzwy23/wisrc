package com.wisrc.purchase.webapp.service;

import java.util.List;
import java.util.Map;

public interface WarehouseService {
    Map getWarehouseNameMap(List<String> warehouseIdsList) throws Exception;

    Map getTotal(String date, List skuIds) throws Exception;
}
