package com.wisrc.warehouse.webapp.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wisrc.warehouse.webapp.vo.stockVO.SkuStockDetailVO;
import com.wisrc.warehouse.webapp.vo.stockVO.SkuVO;
import com.wisrc.warehouse.webapp.dao.WsRmpWarehouseStockSumDao;
import com.wisrc.warehouse.webapp.entity.MskuToSkuEntity;
import com.wisrc.warehouse.webapp.entity.WsRmpWarehouseStockSumEntity;
import com.wisrc.warehouse.webapp.service.WsRmpWarehouseStockSumService;
import com.wisrc.warehouse.webapp.utils.PageData;
import com.wisrc.warehouse.webapp.utils.Result;
import com.wisrc.warehouse.webapp.vo.DetailVO;
import com.wisrc.warehouse.webapp.vo.MskuFbaVO;
import com.wisrc.warehouse.webapp.vo.MskuShopVO;
import com.wisrc.warehouse.webapp.vo.TotalDetailVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class WsRmpWarehouseStockSumServiceImpl implements WsRmpWarehouseStockSumService {
    @Autowired
    private WsRmpWarehouseStockSumDao wsRmpWarehouseStockSumDao;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public LinkedHashMap getStockDetail(String date, String shopName, String keyword, int orderFlag) {

        Set<String> skuList = new HashSet<>();
        List<String> skuId = wsRmpWarehouseStockSumDao.getSkuId(date, keyword);
        List<String> mskuId = wsRmpWarehouseStockSumDao.getMskuSku(date, keyword, shopName);
        for (String sku : skuId) {
            skuList.add(sku);
        }
        for (String sku : mskuId) {
            skuList.add(sku);
        }
        List<String> skuIdList = new ArrayList<>();
        for (String sku : skuList) {
            skuIdList.add(sku);
        }
        List<WsRmpWarehouseStockSumEntity> list = wsRmpWarehouseStockSumDao.getStockDetail(date, skuIdList, orderFlag);
        for (WsRmpWarehouseStockSumEntity entity : list) {
            List<MskuToSkuEntity> entityList = wsRmpWarehouseStockSumDao.getMskuList(entity.getSkuId(), date);
            entity.setMskuList(entityList);
        }
        PageInfo pageInfo = new PageInfo(list);
        return PageData.pack(pageInfo.getTotal(), pageInfo.getPages(), "wsRmpWarehouseStockSumList", list);

    }

    @Override
    public LinkedHashMap getStockDetail(int pageNum, int pageSize, String date, String shopName, String keyword, int orderFlag) {
        Set<String> skuList = new HashSet<>();
        List<String> skuId = new ArrayList<>();
        List<String> mskuId = new ArrayList<>();
        if (shopName == null) {
            skuId = wsRmpWarehouseStockSumDao.getSkuId(date, keyword);
        }
        mskuId = wsRmpWarehouseStockSumDao.getMskuSku(date, keyword, shopName);
        for (String sku : skuId) {
            skuList.add(sku);
        }
        for (String sku : mskuId) {
            skuList.add(sku);
        }
        List<String> skuIdList = new ArrayList<>();
        for (String sku : skuList) {
            skuIdList.add(sku);
        }
        PageHelper.startPage(pageNum, pageSize);
        List<WsRmpWarehouseStockSumEntity> list = wsRmpWarehouseStockSumDao.getStockDetail(date, skuIdList, orderFlag);
        for (WsRmpWarehouseStockSumEntity entity : list) {
            List<MskuToSkuEntity> entityList = wsRmpWarehouseStockSumDao.getMskuList(entity.getSkuId(), date);
            entity.setMskuList(entityList);
        }
        PageInfo pageInfo = new PageInfo(list);
        return PageData.pack(pageInfo.getTotal(), pageInfo.getPages(), "wsRmpWarehouseStockSumList", list);
    }

    @Override
    public LinkedHashMap getTotal(String date, List skuIds) {
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        List<TotalDetailVO> skuList = wsRmpWarehouseStockSumDao.getTotal(date, skuIds);
        List<TotalDetailVO> mskuList = wsRmpWarehouseStockSumDao.getMskuTotal(date, skuIds);
        List<TotalDetailVO> finalList = new ArrayList<>();
        for (int i = 0; i < skuList.size(); i++) {
            for (int j = 0; j < mskuList.size(); j++) {
                if (skuList.get(i).getStoreSku().equals(mskuList.get(j).getStoreSku())) {
                    skuList.get(i).setTotalQty(skuList.get(i).getTotalQty() + mskuList.get(j).getTotalQty());
                    finalList.add(skuList.get(i));
                }
            }
        }
        for (TotalDetailVO vo : skuList) {
            map.put(vo.getStoreSku(), vo.getTotalQty());
        }
        return map;
    }

    @Override
    public Result addStockSum(SkuVO vo) {
        wsRmpWarehouseStockSumDao.addStockSum(vo);
        return Result.success();
    }

    @Override
    public List<DetailVO> getDetail(String skuId, String warehouseId) {
        List<DetailVO> list = wsRmpWarehouseStockSumDao.getDetail(skuId, warehouseId);
        return list;
    }

    @Override
    public List<WsRmpWarehouseStockSumEntity> getAllRecord() {
        List<WsRmpWarehouseStockSumEntity> list = wsRmpWarehouseStockSumDao.getAllRecord();
        for (WsRmpWarehouseStockSumEntity entity : list) {
            List<MskuToSkuEntity> entityList = wsRmpWarehouseStockSumDao.getMskuToSkuList(entity.getSkuId());
            entity.setMskuList(entityList);
        }
        return list;
    }

    @Override
    public void addMsukStock(MskuToSkuEntity msku) {
        wsRmpWarehouseStockSumDao.addMsukStock(msku);
    }

    @Override
    public void deleteRecord(String dataDt) {
        wsRmpWarehouseStockSumDao.deleteSkuRecord(dataDt);
        wsRmpWarehouseStockSumDao.deleteMskuRecord(dataDt);
    }

    @Override
    public void deleteStockDetail(String dataDt) {
        wsRmpWarehouseStockSumDao.deleteStockDetail(dataDt);
    }

    @Override
    public List<SkuStockDetailVO> getStockDetailVO() {
        return wsRmpWarehouseStockSumDao.getStockDetailVO();
    }

    @Override
    public void addSkuStockDetail(SkuStockDetailVO entity) {
        wsRmpWarehouseStockSumDao.addSkuStockDetail(entity);
    }

    @Override
    public void batchInsert(Date dataDt) {
        wsRmpWarehouseStockSumDao.batchInsert(dataDt);
    }

    @Override
    public List<MskuFbaVO> getMskuFba(List<MskuShopVO> mskuShopVOList) {
        List<MskuFbaVO> list = new ArrayList<>();
        for (MskuShopVO vo : mskuShopVOList) {
            MskuFbaVO fbaVO = wsRmpWarehouseStockSumDao.getMskuFba(vo.getMskuId(), vo.getShopId(), vo.getFnSkuId());
            if (fbaVO != null) {
                if (fbaVO.getFnSkuId() == null || "".equals(fbaVO.getFnSkuId())) {
                    fbaVO.setFnSkuId(vo.getFnSkuId());
                }
                list.add(fbaVO);
            }
        }
        return list;
    }
}
