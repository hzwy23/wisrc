package com.wisrc.warehouse.webapp.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wisrc.warehouse.webapp.service.SkuInfoService;
import com.wisrc.warehouse.webapp.dao.WarehouseOutEnterInfoDao;
import com.wisrc.warehouse.webapp.entity.WarehouseOutEnterInfoEntity;
import com.wisrc.warehouse.webapp.service.WarehouseOutEnterInfoService;
import com.wisrc.warehouse.webapp.utils.PageData;
import com.wisrc.warehouse.webapp.utils.Result;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class WarehouseOutEnterInfoServiceImpl implements WarehouseOutEnterInfoService {

    @Autowired
    private WarehouseOutEnterInfoDao warehouseOutEnterInfoDao;
    @Autowired
    private SkuInfoService skuInfoService;

    @Override
    public LinkedHashMap getWarehouseOutEnterInfoAll(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<WarehouseOutEnterInfoEntity> list = warehouseOutEnterInfoDao.getWarehouseOutEnterInfoAll();
        PageInfo pageInfo = new PageInfo(list);
        /*if (list.size() > 0) {
            getSkuName(list);
        }*/
        /*List<WarehouseOutEnterInfoVO> result = new ArrayList<>();
        for (WarehouseOutEnterInfoEntity m : list) {
            result.add(WarehouseOutEnterInfoVO.toVO(m));
        }*/

        return PageData.pack(pageInfo.getTotal(), pageInfo.getPages(), "warehouseOutEnterInfoList", list);
    }

    @Override
    public List<WarehouseOutEnterInfoEntity> getWarehouseOutEnterInfoAll() {
        List<WarehouseOutEnterInfoEntity> list = warehouseOutEnterInfoDao.getWarehouseOutEnterInfoAll();
        /*if (list.size() > 0) {
            getSkuName(list);
        }*/
        return list;
    }

  /*  @Override
    public List<WarehouseOutEnterInfoEntity> getDetailById(int pageNum, int pageSize, String skuId) {
        PageHelper.startPage(pageNum, pageSize);
        List<WarehouseOutEnterInfoEntity> list = warehouseOutEnterInfoDao.getDetailById(skuId);
        return list;
    }

    @Override
    public List<WarehouseOutEnterInfoEntity> getDetailById(String skuId) {
        return warehouseOutEnterInfoDao.getDetailById(skuId);
    }*/

    @Override
    public List<WarehouseOutEnterInfoEntity> getStockWaterByPage(int num, int size, String skuId, String warehouseId) {
        PageHelper.startPage(num, size);
        List<WarehouseOutEnterInfoEntity> list = warehouseOutEnterInfoDao.getStockWater(skuId, warehouseId);
        /*if (list.size() > 0) {
            getSkuName(list);
        }*/
        return list;
    }

    @Override
    public List<WarehouseOutEnterInfoEntity> getStockWater(String skuId, String warehouseId) {
        List<WarehouseOutEnterInfoEntity> list = warehouseOutEnterInfoDao.getStockWater(skuId, warehouseId);
        /*if (list.size() > 0) {
            getSkuName(list);
        }*/
        return list;
    }

    @Override
    public LinkedHashMap getWarehouseOutEnterInfoAll(int pageNum, int pageSize, String documentType, int outEnterType, String warehouseId, Timestamp createTimeBegin, Timestamp createTimeEnd, String keyWord) {
        PageHelper.startPage(pageNum, pageSize);
        List<WarehouseOutEnterInfoEntity> list = warehouseOutEnterInfoDao.search(documentType, outEnterType, warehouseId, createTimeBegin, createTimeEnd, keyWord);
        PageInfo pageInfo = new PageInfo(list);
        /*if (list.size() > 0) {
            getSkuName(list);
        }*/
        /*List<WarehouseOutEnterInfoVO> result = new ArrayList<>();
        for (WarehouseOutEnterInfoEntity m : list) {
            result.add(WarehouseOutEnterInfoVO.toVO(m));
        }*/
        return PageData.pack(pageInfo.getTotal(), pageInfo.getPages(), "warehouseOutEnterInfoList", list);
    }

    public void getSkuName(List<WarehouseOutEnterInfoEntity> list) {
        String[] arr = {};
        for (WarehouseOutEnterInfoEntity entity : list) {
            arr = (String[]) ArrayUtils.add(arr, entity.getSkuId());
        }
        Result result = skuInfoService.getSkuName(arr);
        Map map = (Map) result.getData();

        for (WarehouseOutEnterInfoEntity entity : list) {
            entity.setSkuName((String) map.get(entity.getSkuId()));
        }
    }

}
