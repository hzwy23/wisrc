package com.wisrc.warehouse.webapp.service;

import com.wisrc.warehouse.webapp.entity.WarehouseOutEnterInfoEntity;

import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.List;

public interface WarehouseOutEnterInfoService {
    LinkedHashMap getWarehouseOutEnterInfoAll(int pageNum, int pageSize, String documentType, int outEnterType, String warehouseId, Timestamp createTimeBegin, Timestamp createTimeEnd, String keyWord);

    LinkedHashMap getWarehouseOutEnterInfoAll(int pageNum, int pageSize);

    List<WarehouseOutEnterInfoEntity> getWarehouseOutEnterInfoAll();

   /* List<WarehouseOutEnterInfoEntity> getDetailById(int pageNum, int pageSize, String skuId);

    List<WarehouseOutEnterInfoEntity> getDetailById(String skuId);*/

    List<WarehouseOutEnterInfoEntity> getStockWaterByPage(int num, int size, String skuId, String warehouseId);

    List<WarehouseOutEnterInfoEntity> getStockWater(String skuId, String warehouseId);
}
