package com.wisrc.wms.webapp.utils;

import com.wisrc.wms.webapp.controller.WmsStockController;
import com.wisrc.wms.webapp.service.StockReturnService;
import com.wisrc.wms.webapp.vo.Entity;
import com.wisrc.wms.webapp.vo.ReturnVO.StockReturnVO;
import com.wisrc.wms.webapp.vo.StockQueryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 本地手动刷新库存信息
 */
@Component
public class LocalStockUtil {

    @Autowired
    private WmsStockController wmsStockController;

    @Autowired
    private StockReturnService stockReturnService;

    public Result localRefreshStockInfo(List<StockQueryVO> stockQueryVOS) {
        Entity entity = new Entity();
        List<StockReturnVO> stockReturnVOS = new ArrayList<>();
        for (StockQueryVO stockQueryVO : stockQueryVOS) {
            entity.setWhCode(stockQueryVO.getWarehouseId());
            entity.setGoodsCode(stockQueryVO.getSkuId());
            Result warehouseStock = wmsStockController.getWarehouseStock(entity);
            if (warehouseStock.getCode() == 200) {
                List<Object> wmsReturnDate = (List<Object>) warehouseStock.getData();
                for (Object o : wmsReturnDate) {
                    Map map = (Map) o;
                    StockReturnVO stockReturnVO = new StockReturnVO();
                    stockReturnVO.setWarehouseId((String) map.get("erpWhCode"));
                    stockReturnVO.setWarehouseName((String) map.get("erpWhName"));
                    stockReturnVO.setSubWarehouseId((String) map.get("erpSectionCode"));
                    stockReturnVO.setSubWarehouseName((String) map.get("erpSectionName"));
                    stockReturnVO.setWarehouseZoneId((String) map.get("zoneCode"));
                    stockReturnVO.setWarehouseZoneName((String) map.get("zoneName"));
                    stockReturnVO.setWarehousePositionId((String) map.get("locCode"));
                    stockReturnVO.setSkuId((String) map.get("goodsCode"));
                    stockReturnVO.setSkuName((String) map.get("goodsName"));
                    stockReturnVO.setEnterBatch((String) map.get("inBatchNo"));
                    stockReturnVO.setProductionBatch((String) map.get("productionBatchNo"));
                    stockReturnVO.setSumStock((Integer) map.get("totalQuantity"));
                    stockReturnVO.setEnableStockNum((Integer) map.get("availableNum"));
                    stockReturnVO.setFreezeStockNum((Integer) map.get("frozenNum"));
                    stockReturnVO.setAssignedNum((Integer) map.get("allocatedNum"));
                    stockReturnVO.setWaitUpNum((Integer) map.get("paNum"));
                    stockReturnVO.setReplenishmentWaitDownNum((Integer) map.get("rpOutNum"));
                    stockReturnVO.setReplenishmentWaitUpNum((Integer) map.get("rpInNum"));
                    stockReturnVO.setFnSkuId((String) map.get("fnCode"));
                    stockReturnVO.setbId((Integer) map.get("balanceId"));
                    stockReturnVOS.add(stockReturnVO);
                }
            }
        }

        Result result = stockReturnService.refreshLocalStock(stockReturnVOS);
        return result;
    }
}
