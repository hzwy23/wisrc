package com.wisrc.rules.webapp.service;

import java.util.List;
import java.util.Map;

public interface RulesWarehouseService {
    List warehouseSelector() throws Exception;

    Map getWarehouseName(List warehouseIds) throws Exception;
}
