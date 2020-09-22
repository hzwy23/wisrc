package com.wisrc.replenishment.webapp.service.impl;

import com.google.gson.Gson;
import com.wisrc.replenishment.webapp.dao.FbaReplenishmentInfoDao;
import com.wisrc.replenishment.webapp.dao.WaybillInfoDao;
import com.wisrc.replenishment.webapp.entity.FbaReplenishmentInfoEntity;
import com.wisrc.replenishment.webapp.entity.LogisticOfferEnity;
import com.wisrc.replenishment.webapp.entity.ShipmentEnity;
import com.wisrc.replenishment.webapp.entity.ShipmentInfoEnity;
import com.wisrc.replenishment.webapp.service.CrawlerService;
import com.wisrc.replenishment.webapp.service.ExternalService;
import com.wisrc.replenishment.webapp.service.MskuInfoService;
import com.wisrc.replenishment.webapp.utils.Result;
import com.wisrc.replenishment.webapp.utils.Time;
import com.wisrc.replenishment.webapp.service.externalService.MskuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ExternalServiceimpl implements ExternalService {
    @Autowired
    private FbaReplenishmentInfoDao fbaReplenishmentInfoDao;
    @Autowired
    private WaybillInfoDao waybillInfoDao;
    @Autowired
    private MskuInfoService mskuInfoService;
    @Autowired
    private MskuService mskuService;
    @Autowired
    private CrawlerService crawlerService;

    @Override
    public List<LogisticOfferEnity> getAllLogisticOffer() {
        return fbaReplenishmentInfoDao.getAllLogisticOffer();
    }

    @Override
    @Transactional(transactionManager = "retailReplenishmentTransactionManager")
    public void updateWaybill(List<Map> tracingRecordMapList) {
        if (tracingRecordMapList != null && tracingRecordMapList.size() > 0) {
            for (Map tracingRecordMap : tracingRecordMapList) {
                String waybillId = waybillInfoDao.getWayBillId(tracingRecordMap);
                if (tracingRecordMap.get("status") != null) {
                    if (tracingRecordMap.get("status").equals("Success")) {
                        waybillInfoDao.updateWaybillTransfer(tracingRecordMap);
                        tracingRecordMap.put("waybillId", waybillId);
                        waybillInfoDao.updateTransferOrder(tracingRecordMap);
                    }
                }
                if (tracingRecordMap.get("expectDeliveryDate") != null) {
                    String expectDeliveryDate = (String) tracingRecordMap.get("expectDeliveryDate");
                    waybillInfoDao.updateFright(expectDeliveryDate, waybillId);
                }
                String logisticsId = waybillInfoDao.getId(tracingRecordMap);
                if (logisticsId != null) {
                    waybillInfoDao.updateTrackInfo(tracingRecordMap);
                } else {
                    waybillInfoDao.insertTrackInfo(tracingRecordMap);
                }
            }
        }
    }

    @Override
    public List<ShipmentEnity> getShipmentEnity() {
        List<ShipmentEnity> shipmentEnityList = fbaReplenishmentInfoDao.getAllShipmentEnity();
        Result shopResult = mskuInfoService.getShop(null);
        Map<String, String> shopMap = new HashMap<>();
        if (shopResult.getCode() == 200) {
            Map map = (Map) shopResult.getData();
            if (map != null) {
                List shopList = (List) map.get("storeInfoList");
                if (shopList != null) {
                    for (Object shopObject : shopList) {
                        Map shop = (Map) shopObject;
                        shopMap.put((String) shop.get("shopId"), (String) shop.get("sellerNo"));
                    }
                }
            }
        }
        for (ShipmentEnity shipmentEnity : shipmentEnityList) {
            shipmentEnity.setSellerId(shopMap.get(shipmentEnity.getShopId()));
        }
        return shipmentEnityList;
    }

    @Override
    @Transactional(transactionManager = "retailReplenishmentTransactionManager")
    public void batchUpdateSignNum(List<ShipmentInfoEnity> shipmentInfoEnityList) {
        String time = Time.getCurrentDateTime();
        if (shipmentInfoEnityList != null && shipmentInfoEnityList.size() > 0) {
            for (ShipmentInfoEnity shipmentInfoEnity : shipmentInfoEnityList) {
                List<FbaReplenishmentInfoEntity> fbaReplenishmentInfoEntityList = fbaReplenishmentInfoDao.findByBatchBumber(shipmentInfoEnity.getShipmentId());
                if (fbaReplenishmentInfoEntityList == null) {
                    break;
                }
                for (FbaReplenishmentInfoEntity fbaReplenishmentInfoEntity : fbaReplenishmentInfoEntityList) {
                    if (fbaReplenishmentInfoEntity == null) {
                        continue;
                    }
                    List<Map> mapList = shipmentInfoEnity.getShipmentInfoItemList();
                    for (Map map : mapList) {
                        String msku = (String) map.get("sellerSKU");
                        String fbaReplenishmentId = fbaReplenishmentInfoEntity.getFbaReplenishmentId();
                        String shopId = fbaReplenishmentInfoEntity.getShopId();
                        Integer signInQuantity = (Integer) map.get("quantityReceived");
                        Integer count = fbaReplenishmentInfoDao.batchUpdateSignNum(msku, fbaReplenishmentId, shopId, signInQuantity);
                    }
                    String status = shipmentInfoEnity.getShipmentStatus();
                    if ("CLOSED".equals(status)) {
                        fbaReplenishmentInfoDao.updateStatusByBatchNumber(4, shipmentInfoEnity.getShipmentId(), time);
                        String waybillId = fbaReplenishmentInfoDao.findWayBillIdByFbaId(fbaReplenishmentInfoEntity.getFbaReplenishmentId());
                        List<String> fbaIds = fbaReplenishmentInfoDao.getFbaReplementIdByWaybill(waybillId);
                        if (fbaIds != null && fbaIds.size() > 1) {
                            boolean flag = true;
                            for (String fbaReplementId : fbaIds) {
                                FbaReplenishmentInfoEntity fbaRepleInfoEntity = fbaReplenishmentInfoDao.findById(fbaReplementId);
                                if (fbaRepleInfoEntity == null) {
                                    continue;
                                }
                                if (fbaRepleInfoEntity.getStatusCd() != 4) {
                                    flag = false;
                                    break;
                                }
                            }
                            if (flag) {
                                waybillInfoDao.updateStatus(3, waybillId, time);
                            }
                        } else {
                            waybillInfoDao.updateStatus(3, waybillId, time);
                        }
                    }
                }
            }
        }
    }

    @Override
    public Result getWaybillTransferInfo(String waybillId) {
        Result shopResult = mskuService.getShop(null);
        List<Map> paraMapList = new ArrayList<>();
        Map sellShopMap = new HashMap<>();
        if (shopResult.getCode() == 200) {
            Map map = (Map) shopResult.getData();
            if (map != null) {
                List shopList = (List) map.get("storeInfoList");
                if (shopList != null) {
                    for (Object shopObject : shopList) {
                        Map shop = (Map) shopObject;
                        sellShopMap.put((String) shop.get("shopId"), (String) shop.get("sellerNo"));
                    }
                }
            }
        }
        List<Map> bacthNumberList = fbaReplenishmentInfoDao.getBatchNumberByWayId(waybillId);
//        String shipmentId= (String) map.get("shipmentId");
//        String sellerId=(String) map.get("sellerId");
        if (bacthNumberList != null && bacthNumberList.size() > 0) {
            for (Map map : bacthNumberList) {
                String fbaReplenishmentId = (String) map.get("fbaReplenishmentId");
                String bacthNumber = (String) map.get("bacthNumber");
                Map<String, String> paraMap = new HashMap();
                if (fbaReplenishmentId != null && bacthNumber != null) {
                    String shopId = fbaReplenishmentInfoDao.getShopId(fbaReplenishmentId);
                    paraMap.put("shipmentId", bacthNumber);
                    paraMap.put("sellerId", (String) sellShopMap.get(shopId));
                    paraMapList.add(paraMap);
                }

            }
        }
        Gson gson = new Gson();
        Result result = crawlerService.updateMskuSaleStock(gson.toJson(paraMapList));
        return result;
    }

    @Override
    public Result updateStatus(String[] waybillIds) {
        String time = Time.getCurrentDateTime();
        for (String waybillId : waybillIds) {
            waybillInfoDao.updateStatusAndTime(time, 3, waybillId);
        }
        return Result.success(null);
    }

    @Override
    public List<Map> getShipment() {
        List<Map> mapList = fbaReplenishmentInfoDao.getWaybillAndShipmentId();
        return mapList;
    }
}
