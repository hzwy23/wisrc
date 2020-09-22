package com.wisrc.warehouse.webapp.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wisrc.warehouse.webapp.service.SkuInfoService;
import com.wisrc.warehouse.webapp.vo.stockVO.ProxyVirtual;
import com.wisrc.warehouse.webapp.dao.StockDao;
import com.wisrc.warehouse.webapp.dao.WsRmpWarehouseStockSumDao;
import com.wisrc.warehouse.webapp.entity.MskuToSkuEntity;
import com.wisrc.warehouse.webapp.entity.StockEntity;
import com.wisrc.warehouse.webapp.entity.WarehouseStockManagerSyncEntity;
import com.wisrc.warehouse.webapp.entity.WsRmpWarehouseStockSumEntity;
import com.wisrc.warehouse.webapp.service.StockService;
import com.wisrc.warehouse.webapp.utils.Result;
import com.wisrc.warehouse.webapp.vo.SkuWarehouseVo;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class StockServiceImpl implements StockService {
    @Autowired
    private StockDao stockDao;
    @Autowired
    private SkuInfoService skuInfoService;
    @Autowired
    private WsRmpWarehouseStockSumDao wsRmpWarehouseStockSumDao;


    @Override
    public List<StockEntity> findAll(String warehouseId, String keyword) {

        List<StockEntity> list = stockDao.findAll(warehouseId, keyword);
        /*if (list.size() > 0) {
            getSkuName(list);
        }*/
        return list;
    }


    @Override
    public List<StockEntity> findAllByPage(int pageNum, int pageSize, String warehouseId, String keyword) {
//        PageHelper.startPage(pageNum, pageSize);
        List<StockEntity> stockList = stockDao.findStockByCondition(warehouseId, keyword);
        /*if (stockList.size() > 0) {
            getSkuName(stockList);
        }*/
        return stockList;
    }

    @Override

    public Result getStockList(int pageNum, int pageSize, String warehouseId, String keyword) {
        PageHelper.startPage(pageNum, pageSize);
        List<StockEntity> stockList = stockDao.findStockByCondition(warehouseId, keyword);
        /*if (stockList.size() > 0) {
            getSkuName(stockList);
        }*/
        PageInfo pageInfo = new PageInfo(stockList);
        LinkedHashMap dataMap = new LinkedHashMap();
        long total = pageInfo.getTotal();
        int pages = pageInfo.getPages();
        dataMap.put("total", total);
        dataMap.put("pages", pages);
        dataMap.put("stockList", stockList);
        return Result.success(dataMap);
    }

    @Override
    public List<StockEntity> getStockBySku(List skuIdList) {
        List<StockEntity> list = stockDao.getStockBySku(skuIdList);
        /*if (list.size() > 0) {
            getSkuName(list);
        }*/
        return list;
    }


    /*@Override
    public String getWarehouseId(String stockId) {
        return stockDao.getWarehouseId(stockId);
    }*/

    @Override
    public boolean hasSkuBySubwarehouseId(String subWarhouseId) {
        return stockDao.hasSkuBySubwarehouseId(subWarhouseId) > 0;
    }

    @Override
    public List<ProxyVirtual> getVirtualStock(String skuId, String date) {
        return stockDao.getVirtualStock(skuId, date);
    }

    @Override
    public List<Map> getStockTotalBySku(List<String> skuIdList) {
      /*  String comodityIds = "";
        for (String commodityId : conditnIdSet) {
            comodityIds += "'" + commodityId + "'" + ",";
        }
        if (comodityIds.endsWith(",")) {
            int index = comodityIds.lastIndexOf(",");
            comodityIds = comodityIds.substring(0, index);
        }*/
        if (skuIdList == null || skuIdList.size() <= 0) {
            return null;
        }
        String skuIds = "";
        for (String skuId : skuIdList) {
            skuIds += "'" + skuId + "'" + ",";
        }
        if (skuIds.endsWith(",")) {
            int index = skuIds.lastIndexOf(",");
            skuIds = skuIds.substring(0, index);
        }
        List<Map> mapList = new ArrayList<>();
        List<MskuToSkuEntity> entityList = wsRmpWarehouseStockSumDao.getMskuBySkuList(skuIds);
        List<Map> panyuNumList = wsRmpWarehouseStockSumDao.getPanyuNum(skuIds);
        Map panyuNumMap = new HashMap();
        for (Map map : panyuNumList) {
            panyuNumMap.put(map.get("skuId"), map.get("panyuQuantity"));
        }
        for (MskuToSkuEntity mskuToSkuEntity : entityList) {
            Map map = new HashMap();
            map.put("skuId", mskuToSkuEntity.getSkuId());
            Object num = (Object) panyuNumMap.get(mskuToSkuEntity.getSkuId());
            Integer panyuNum = 0;
            if (num != null) {
                panyuNum = Integer.parseInt(num.toString());
            }
            map.put("totalSum", mskuToSkuEntity.getFbaReturnQty() + mskuToSkuEntity.getFbaTransportQty() + mskuToSkuEntity.getFbaStockQty() + panyuNum);
            mapList.add(map);
        }
        return mapList;
    }

    @Override
    public List<Map> getStockTotalBySkuAndDate(List<Map> paramaterList) {
        List<Map> list = new ArrayList<>();
        if (paramaterList == null) {
            return null;
        }
        for (Map map : paramaterList) {
            String skuId = (String) map.get("skuId");
            String date = (String) map.get("date");
            if (skuId == null || date == null) {
                continue;
            }
            WsRmpWarehouseStockSumEntity wsRmpWarehouseStockSumEntity = wsRmpWarehouseStockSumDao.getStockDetailBySkuId(skuId, date);
            //MskuToSkuEntity mskuToSkuEntity = wsRmpWarehouseStockSumDao.getMskuBySkuAndDate(skuId, date);
            if (wsRmpWarehouseStockSumEntity != null) {
                Map wsMap = new HashMap();
                wsMap.put(skuId + date, wsRmpWarehouseStockSumEntity.getTotalQty());
                list.add(wsMap);
            }
        }
        return list;
    }

    @Override
    public List<Map> getStockBySkuAndWarehouse(List<SkuWarehouseVo> paramaterList) {
        List<Map> mapList = new ArrayList<>();
        if (paramaterList == null || paramaterList.size() <= 0) {
            return mapList;
        }
        for (SkuWarehouseVo skuWarehouseVo : paramaterList) {
            Double eableStockNum = wsRmpWarehouseStockSumDao.getStockNum(skuWarehouseVo);
            if (eableStockNum == null) {
                eableStockNum = 0.0;
            }
            Map map = new HashMap();
            map.put(skuWarehouseVo.getSkuId(), eableStockNum);
            mapList.add(map);
        }
        return mapList;
    }

    /*@Override
    public boolean hasSkuByWarehouseId(String warehouseId) {
        return stockDao.hasSkuByWarehouseId(warehouseId) > 0;
    }*/

    @Override
    public StockEntity getStockByCond(String skuId, String warehouseId) {
        return stockDao.getStockByCond(skuId, warehouseId);
    }

    public void getSkuName(List<StockEntity> list) {
        String[] arr = {};
        for (StockEntity entity : list) {
            arr = (String[]) ArrayUtils.add(arr, entity.getSkuId());
        }
        Result result = skuInfoService.getSkuName(arr);
        Map map = (Map) result.getData();

        for (StockEntity entity : list) {
            entity.setSkuName((String) map.get(entity.getSkuId()));
        }
    }

    @Override
    public WarehouseStockManagerSyncEntity getStockInfo(String skuId, String fnCode, String subWarehouseId) {
        return stockDao.getStockInfo(skuId, fnCode, subWarehouseId);
    }
}
