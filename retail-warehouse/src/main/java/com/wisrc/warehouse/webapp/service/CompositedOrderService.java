package com.wisrc.warehouse.webapp.service;

import com.wisrc.warehouse.webapp.utils.Result;

public interface CompositedOrderService {

    Result getInventory(String skuIdTypeC, String warehouserId, Integer needInventoryNum);

    /**
     * 判断库存是否充足
     *
     * @param fnSku
     * @param warehouseId
     * @param number
     * @return
     */
    Result judgeStockByFnSkuAndFnSkuId(String fnSku, String warehouseId, Integer number);
}
