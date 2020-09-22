package com.wisrc.warehouse.webapp.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wisrc.warehouse.webapp.service.MskuService;
import com.wisrc.warehouse.webapp.service.SkuInfoService;
import com.wisrc.warehouse.webapp.service.StockDetailService;
import com.wisrc.warehouse.webapp.dao.StockDetailDao;
import com.wisrc.warehouse.webapp.entity.FbaStockDetailEntity;
import com.wisrc.warehouse.webapp.utils.PageData;
import com.wisrc.warehouse.webapp.utils.Result;
import com.wisrc.warehouse.webapp.vo.FnSkuStockVO;
import com.wisrc.warehouse.webapp.vo.StockDetailVO;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class StockDetailServiceImpl implements StockDetailService {
    @Autowired
    private StockDetailDao stockDetailDao;

    @Autowired
    private SkuInfoService skuInfoService;

    @Autowired
    private MskuService mskuService;


    @Override
    public LinkedHashMap getFbaDetailById(int num, int size, String skuId, String warehouseId) {
        PageHelper.startPage(num, size);
        List<FbaStockDetailEntity> list = stockDetailDao.getFbaDetailById(skuId, warehouseId);
        PageInfo pageInfo = new PageInfo(list);
        return PageData.pack(pageInfo.getTotal(), pageInfo.getPages(), "FbaStockDetailEntityList", list);

    }

    @Override
    public LinkedHashMap getDetailById(int num, int size, String skuId, String warehouseId) {
        PageHelper.startPage(num, size);
        List<StockDetailVO> list = stockDetailDao.getDetailById(skuId, warehouseId);
        PageInfo pageInfo = new PageInfo(list);
        return PageData.pack(pageInfo.getTotal(), pageInfo.getPages(), "StockDetailEntityList", list);
    }

    @Override
    public List<FbaStockDetailEntity> getFbaDetailById(String skuId, String warehouseId) {
        List<FbaStockDetailEntity> list = stockDetailDao.getFbaDetailById(skuId, warehouseId);
        return list;
    }

    @Override
    public List<StockDetailVO> getDetailById(String skuId, String warehouseId) {
        List<StockDetailVO> list = stockDetailDao.getDetailById(skuId, warehouseId);
        return list;
    }

    @Override
    public List<FnSkuStockVO> getFnSkuStock(String fnSkuId, String warehouseId) {
        List<FnSkuStockVO> list = stockDetailDao.getFnSkuStock(fnSkuId, warehouseId);
        return list;
    }

    @Override
    public Result updateFbaStockDetail(List<Map> mapList) {
        Result result = mskuService.getWarehouseAndFnsku(mapList);
        if (result.getCode() != 200) {
            return Result.failure(390, "商品接口调用异常", null);
        }
        Map<String, Map> wareFnskuMap = (Map) result.getData();
        if (wareFnskuMap == null) {
            return Result.success();
        }
        if (mapList == null || mapList.size() <= 0) {
            return Result.success();
        }
        if (mapList != null && mapList.size() > 0) {
            for (Map map : mapList) {
                String sellerId = (String) map.get("sellerId");
                String fnsku = (String) map.get("fnsku");
                Integer eableStockNum = (Integer) map.get("eableStockNum");
                Integer unableStockNum = (Integer) map.get("unableStockNum");
                Integer reservedCustomerorders = (Integer) map.get("reservedCustomerorders");
                Integer reservedFcTransfers = (Integer) map.get("reservedFcTransfers");
                Integer reservedFcProcessing = (Integer) map.get("reservedFcProcessing");
                if (eableStockNum == null) {
                    eableStockNum = 0;
                }
                if (unableStockNum == null) {
                    unableStockNum = 0;
                }
                if (reservedCustomerorders == null) {
                    reservedCustomerorders = 0;
                }
                if (reservedFcTransfers == null) {
                    reservedFcTransfers = 0;
                }
                if (reservedFcProcessing == null) {
                    reservedFcProcessing = 0;
                }
                int stockTotalQuantity = eableStockNum + unableStockNum + reservedCustomerorders + reservedFcTransfers + reservedFcProcessing;
                int useableQuantity = reservedFcTransfers + reservedFcProcessing + eableStockNum;
                int unableQuantity = reservedCustomerorders + unableStockNum;
                Map finalMap = wareFnskuMap.get(sellerId + fnsku);
                if (finalMap != null) {
                    String warehouseId = (String) finalMap.get("warehouseId");
                    String skuId = (String) finalMap.get("skuId");
                    String shopId = (String) finalMap.get("shopId");
                    if (warehouseId != null) {
//                        List<Map> stockMap = stockDetailDao.getByCond(warehouseId, fnsku, skuId, shopId);
                        stockDetailDao.deleteWareByCond(warehouseId, skuId, shopId, fnsku);
                        stockDetailDao.insertWarehouse(warehouseId, fnsku, skuId, stockTotalQuantity, useableQuantity, unableQuantity, shopId, null);
                       /* if (stockMap != null && stockMap.size() > 0) {
                            stockDetailDao.updateStockDetail(warehouseId, fnsku, skuId, stockTotalQuantity, useableQuantity, unableQuantity, shopId, msku);
                        } else {

                        }*/
                    }
                }
            }
        }
        return Result.success();
    }

    @Override
    public List<FnSkuStockVO> getFnSkuStockOversea(String skuId) {
        return stockDetailDao.getFnSkuStockOversea(skuId);
    }

    public void getSkuName(List<FnSkuStockVO> list) {
        String[] arr = {};
        for (FnSkuStockVO entity : list) {
            arr = (String[]) ArrayUtils.add(arr, entity.getSkuId());
        }
        Result result = skuInfoService.getSkuName(arr);
        Map map = (Map) result.getData();
        for (FnSkuStockVO entity : list) {
            entity.setSkuName((String) map.get(entity.getSkuId()));
        }
    }


    @Override
    public List<FnSkuStockVO> getSkuStock(String skuId, String warehouseId) {
        List<FnSkuStockVO> list = stockDetailDao.getSkuStock(skuId, warehouseId);
        return list;
    }
}
