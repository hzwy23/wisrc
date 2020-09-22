package com.wisrc.warehouse.webapp.service.impl;

import com.wisrc.warehouse.webapp.service.CompositedOrderService;
import com.wisrc.warehouse.webapp.service.SkuInfoService;
import com.wisrc.warehouse.webapp.service.StockDetailService;
import com.wisrc.warehouse.webapp.dao.StockDao;
import com.wisrc.warehouse.webapp.entity.StockEntity;
import com.wisrc.warehouse.webapp.service.externalService.ExternalMskuService;
import com.wisrc.warehouse.webapp.utils.Result;
import com.wisrc.warehouse.webapp.vo.FnSkuStockVO;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CompositedOrderServiceImpl implements CompositedOrderService {

    @Autowired
    private StockDao stockDao;

    @Autowired
    private SkuInfoService skuInfoService;

    @Autowired
    private StockDetailService stockDetailService;

    @Autowired
    private ExternalMskuService mskuService;

    @Override
    public Result getInventory(String skuIdTypeC, String warehouseId, Integer needInventoryNum) {

        StockEntity stock = stockDao.getStockByCond(skuIdTypeC, warehouseId);
        int enableStockNumber;
        if (stock == null) {
            enableStockNumber = 0;
        } else {
            enableStockNumber = stock.getEnableStockNum();
        }
        if (enableStockNumber >= needInventoryNum) {
            return Result.success(200, "本地库存足够", 1);
        } else {
            int producNum = needInventoryNum - enableStockNumber;
            Result CompositedInventoryResult = skuInfoService.getCompositedSku(skuIdTypeC);
            List productList = (List) CompositedInventoryResult.getData();
            if (productList.size() == 0) {
                return Result.failure(390, "该产品不能加工，库存不足", enableStockNumber);
            }
            for (Object one : productList) {
                String dependencySkuId = (String) ((Map) one).get("dependencySkuId");
                Integer quantity = (Integer) ((Map) one).get("quantity");
                if (quantity == null) {
                    quantity = 0;
                }
                int needNum = quantity * producNum;
                StockEntity stockByCond = stockDao.getStockByCond(dependencySkuId, warehouseId);
                if (stockByCond == null) {
                    return Result.failure(390, "库存不足", enableStockNumber);
                }
                if (stockByCond.getEnableStockNum() == null) {
                    stockByCond.setEnableStockNum(0);
                }
                if (stockByCond.getEnableStockNum() < needNum) {
                    return Result.success(390, "组装库存不足", enableStockNumber);
                }
            }
            return Result.success(200, "组装库存足够", 2);
        }
    }

    @Override
    public Result judgeStockByFnSkuAndFnSkuId(String fnSku, String warehouseId, Integer number) {
        List<FnSkuStockVO> fnSkuStock = stockDetailService.getFnSkuStock(fnSku, warehouseId);
        if (CollectionUtils.isEmpty(fnSkuStock)) {
            return Result.failure(390, "库存不足", 0);
        }
        int stockSum = 0;
        for (FnSkuStockVO fnSkuStockVO : fnSkuStock) {
            stockSum += fnSkuStockVO.getEnableStockNum();
        }
        if (stockSum < number) {
            return Result.failure(390, "库存不足", stockSum);
        } else {
            return Result.success(200, "本地库存足够", 1);
        }
    }
}
