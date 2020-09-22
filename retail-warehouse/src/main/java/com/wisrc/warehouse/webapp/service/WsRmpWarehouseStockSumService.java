package com.wisrc.warehouse.webapp.service;

import com.wisrc.warehouse.webapp.entity.MskuToSkuEntity;
import com.wisrc.warehouse.webapp.entity.WsRmpWarehouseStockSumEntity;
import com.wisrc.warehouse.webapp.utils.Result;
import com.wisrc.warehouse.webapp.vo.DetailVO;
import com.wisrc.warehouse.webapp.vo.MskuFbaVO;
import com.wisrc.warehouse.webapp.vo.MskuShopVO;
import com.wisrc.warehouse.webapp.vo.stockVO.SkuStockDetailVO;
import com.wisrc.warehouse.webapp.vo.stockVO.SkuVO;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

public interface WsRmpWarehouseStockSumService {
    LinkedHashMap getStockDetail(int pageNum, int pageSize, String date, String shopName, String keyword, int orderFlag);

    LinkedHashMap getStockDetail(String date, String shopName, String keyword, int orderFlag);

    LinkedHashMap getTotal(String date, List skuIds);

    Result addStockSum(SkuVO vo);

    List<DetailVO> getDetail(String skuId, String warehouseId);

    List<WsRmpWarehouseStockSumEntity> getAllRecord();

    void addMsukStock(MskuToSkuEntity msku);

    void deleteRecord(String dataDt);

    void deleteStockDetail(String dataDt);

    List<SkuStockDetailVO> getStockDetailVO();

    void addSkuStockDetail(SkuStockDetailVO entity);

    void batchInsert(Date dataDt);

    List<MskuFbaVO> getMskuFba(List<MskuShopVO> mskuShopVOList);
}
