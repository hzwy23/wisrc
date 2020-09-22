package com.wisrc.crawler.webapp.service;

import com.wisrc.crawler.webapp.entity.MskuInfoEnity;
import com.wisrc.crawler.webapp.entity.MskuSaleNumEnity;
import com.wisrc.crawler.webapp.entity.RemoveOrderInfoEnity;
import com.wisrc.crawler.webapp.entity.ShipmentInfoEnity;

import java.util.List;
import java.util.Map;

public interface ShipmentInfoService {


    Map getShipmentTracingRecord(String shipmentId, String shipmentType);

    RemoveOrderInfoEnity getRemoveOrderInfo(String removeOrderId, String sellerId);

    ShipmentInfoEnity getShipmentInfo(String shipmentId, String sellerId);

    MskuInfoEnity getShipmentMskuInfo(String mskuId, String sellerId);

    MskuSaleNumEnity getMskuSaleNum(String mskuId, String sellerId);

    Map getMskuStockInfo(String mskuId, String sellerId);

    List<MskuSaleNumEnity> getBatchMskuSaleNum(List list);

    MskuInfoEnity getShipmentMskuInfoByShopIdAndMskuId(String mskuId, String shopId);

    Map getRemoveTracingRecord(String trackingNumber, String carrier);

    Map getSingleShipmentTracingRecord(String trackingNumber, String carrier);

    Map getSingelMskuStockInfo(String fnsku, String sellerId);

    ShipmentInfoEnity getSingelShipmentInfo(String batchNumber, String sellerId);

    String getShelveData(String asin);
}
