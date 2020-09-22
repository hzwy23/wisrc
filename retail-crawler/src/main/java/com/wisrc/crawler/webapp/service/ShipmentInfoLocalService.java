package com.wisrc.crawler.webapp.service;

import com.wisrc.crawler.webapp.entity.*;

import java.util.List;
import java.util.Map;

public interface ShipmentInfoLocalService {
    TracingRecordEnity getShipmentTracingRecord(String shipmentId, String shipmentType);

    RemoveOrderInfoEnity getRemoveOrderInfo(String removeOrderId, String sellerId);

    ShipmentInfoEnity getShipmentInfo(String shipmentId, String sellerId);

    MskuSaleNumEnity getMskuSaleNum(String mskuId, String sellerId);

    ShipmentStockEnity getMskuStockInfo(String mskuId, String sellerId);

    List<RemoveOrderInfoEnity> getRemoveOrderInfoList(List<RemoveOrderEnity> requestMapList);

    List<TracingRecordEnity> getTracingRecordList(List<Map<String, String>> tracingMapList);

    List<RecentSaleNumEnity> geRecentMskuSaleNum(String stratTime, String endTime, String shopId, String msku);

    List<RecentSaleNumEnity> getPreSevenDaySaleNum(String mskuId, String shopId);

    void updateSignTime(String trackingNumber, String carrier, String fnsku);

    Map getRemoveOrderInfoBatch(List<Map> parameterMapList);

    List<ShipmentTransferEnity> getShipmentTransferInfo(List<Map> mapList);

    void updateWaybillSignTime(String trackingNumber, String wayBillId);
}
