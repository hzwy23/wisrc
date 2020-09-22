package com.wisrc.warehouse.webapp.service;

import com.wisrc.warehouse.webapp.entity.StockEntity;
import com.wisrc.warehouse.webapp.entity.WarehouseStockManagerSyncEntity;
import com.wisrc.warehouse.webapp.utils.Result;
import com.wisrc.warehouse.webapp.vo.SkuWarehouseVo;
import com.wisrc.warehouse.webapp.vo.stockVO.ProxyVirtual;

import java.util.List;
import java.util.Map;

public interface StockService {

    List<StockEntity> findAll(String warehouseId, String keyword);

    List<StockEntity> findAllByPage(int pageNum, int pageSize, String warehouseId, String keyword);

    Result getStockList(int pageNum, int pageSize, String warehouseId, String keyword);

/*    WarehouseOutEnterInfoEntity getDetailById(String skuId);

    List<StockEntity> findDetail(int pageNum, int pageSize, String createDate, String shopId, String keyword);*/

    List<StockEntity> getStockBySku(List skuIdList);

    StockEntity getStockByCond(String skuId, String warehouseId);

    /*String getWarehouseId(String stockId);*/

    /**
     * 查询某个分仓是否还有可用库存
     */
    boolean hasSkuBySubwarehouseId(String subWarhouseId);

    /**
     * 查询仓库是否有可用库存
     */
    /*boolean hasSkuByWarehouseId(String warehouseId);*/

    /**
     * 根据skuId查询该sku在各海外仓库存分布
     *
     * @param skuId
     * @return
     */
    List<ProxyVirtual> getVirtualStock(String skuId, String date);

    List<Map> getStockTotalBySku(List<String> skuIdList);

    List<Map> getStockTotalBySkuAndDate(List<Map> paramaterList);

    List<Map> getStockBySkuAndWarehouse(List<SkuWarehouseVo> paramaterList);

    WarehouseStockManagerSyncEntity getStockInfo(String skuId, String fnCode, String subWarehouseId);
}
