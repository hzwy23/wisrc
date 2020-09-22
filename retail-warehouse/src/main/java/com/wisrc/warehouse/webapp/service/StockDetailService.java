package com.wisrc.warehouse.webapp.service;

import com.wisrc.warehouse.webapp.entity.FbaStockDetailEntity;
import com.wisrc.warehouse.webapp.utils.Result;
import com.wisrc.warehouse.webapp.vo.FnSkuStockVO;
import com.wisrc.warehouse.webapp.vo.StockDetailVO;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface StockDetailService {


    List<FnSkuStockVO> getFnSkuStock(String fnSkuId, String warehouseId);

    Result updateFbaStockDetail(List<Map> mapList);


    LinkedHashMap getFbaDetailById(int num, int size, String skuId, String warehouseId);

    LinkedHashMap getDetailById(int num, int size, String skuId, String warehouseId);

    List<FbaStockDetailEntity> getFbaDetailById(String skuId, String warehouseId);

    List<StockDetailVO> getDetailById(String skuId, String warehouseId);

    List<FnSkuStockVO> getFnSkuStockOversea(String skuId);

    List<FnSkuStockVO> getSkuStock(String skuId, String warehouseId);
}
