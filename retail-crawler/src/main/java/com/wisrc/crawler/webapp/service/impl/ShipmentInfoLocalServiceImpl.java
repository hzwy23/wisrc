package com.wisrc.crawler.webapp.service.impl;

import com.google.gson.Gson;
import com.wisrc.crawler.webapp.dao.ShipmentInfoLocalDao;
import com.wisrc.crawler.webapp.entity.*;
import com.wisrc.crawler.webapp.service.ExternalService.MskuService;
import com.wisrc.crawler.webapp.service.ExternalService.ReplenishmentService;
import com.wisrc.crawler.webapp.service.ShipmentInfoLocalService;
import com.wisrc.crawler.webapp.utils.DateUtil;
import com.wisrc.crawler.webapp.utils.Result;
import com.wisrc.crawler.webapp.utils.Time;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ShipmentInfoLocalServiceImpl implements ShipmentInfoLocalService {
    @Autowired
    private ShipmentInfoLocalDao shipmentInfoLocalDao;
    @Autowired
    private MskuService mskuService;
    @Autowired
    private ReplenishmentService replenishmentService;
    @Autowired
    private RedisTemplate redisTemplate;


    @Override
    public TracingRecordEnity getShipmentTracingRecord(String shipmentId, String shipmentType) {
        return shipmentInfoLocalDao.getShipmentTracingRecord(shipmentId, shipmentType);
    }

    @Override
    public RemoveOrderInfoEnity getRemoveOrderInfo(String removeOrderId, String sellerId) {
        RemoveOrderInfoEnity removeOrderInfoEnity = shipmentInfoLocalDao.getRemoveOrderInfo(removeOrderId, sellerId);
        if (removeOrderInfoEnity == null) {
            return null;
        }
        List<RemoveOrderDetailEnity> removeOrderDetailEnityList = shipmentInfoLocalDao.getRemoveOrderDetail(removeOrderId, sellerId);
        removeOrderInfoEnity.setRemoveOrderShipmentInfoList(removeOrderDetailEnityList);
        return removeOrderInfoEnity;
    }

    @Override
    public ShipmentInfoEnity getShipmentInfo(String shipmentId, String sellerId) {
        ShipmentInfoEnity shipmentInfoEnity = shipmentInfoLocalDao.getShipmentInfo(shipmentId, sellerId);
        if (shipmentInfoEnity == null) {
            return null;
        }
        List<ShipmentInfoDetailEnity> shipmentInfoDetailEnityList = shipmentInfoLocalDao.getShipmentInfoDetail(shipmentId, sellerId);
        shipmentInfoEnity.setShipmentInfoItemList(shipmentInfoDetailEnityList);
        List<String> tracingIds = shipmentInfoLocalDao.getTracingId(shipmentId, sellerId);
        shipmentInfoEnity.setTracingIds(tracingIds);
        return shipmentInfoEnity;
    }

    @Override
    public MskuSaleNumEnity getMskuSaleNum(String mskuId, String sellerId) {
        String nowDate = DateUtil.convertDateToStr(new Date(), DateUtil.DEFAULT_SHORT_DATE_FORMAT);
        MskuSaleNumEnity mskuSaleNumEnity = shipmentInfoLocalDao.getMskuSaleNum(mskuId, sellerId, nowDate);
        return mskuSaleNumEnity;
    }

    @Override
    public ShipmentStockEnity getMskuStockInfo(String mskuId, String sellerId) {
        return shipmentInfoLocalDao.getMskuStockInfo(mskuId, sellerId);
    }

    @Override
    public List<RemoveOrderInfoEnity> getRemoveOrderInfoList(List<RemoveOrderEnity> removeOrderEnityList) {
        Result shopResult = mskuService.getShop(null);
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
        List<RemoveOrderInfoEnity> removeOrderInfoEnityList = new ArrayList<>();
        if (removeOrderEnityList != null && removeOrderEnityList.size() > 0) {
            for (RemoveOrderEnity removeOrderEnity : removeOrderEnityList) {
                RemoveOrderInfoEnity removeOrderInfoEnity = shipmentInfoLocalDao.getRemoveOrderInfo(removeOrderEnity.getRemoveOrderId(), shopMap.get(removeOrderEnity.getShopId()));
                List<RemoveOrderDetailEnity> removeOrderDetailEnityList = shipmentInfoLocalDao.getRemoveOrderDetail(removeOrderEnity.getRemoveOrderId(), shopMap.get(removeOrderEnity.getShopId()));
                if (removeOrderDetailEnityList != null && removeOrderDetailEnityList.size() > 0) {
                    for (RemoveOrderDetailEnity removeOrderDetailEnity : removeOrderDetailEnityList) {
                        String trackInfo = shipmentInfoLocalDao.getTrackByNumAndCar(removeOrderDetailEnity.getTrackingNumber(), removeOrderDetailEnity.getCarrier());
                        removeOrderDetailEnity.setTrackingInfo(trackInfo);
                    }
                }
                if (removeOrderInfoEnity != null) {
                    removeOrderInfoEnity.setRemoveOrderShipmentInfoList(removeOrderDetailEnityList);
                    removeOrderInfoEnityList.add(removeOrderInfoEnity);
                }
            }
            return removeOrderInfoEnityList;
        }
        return null;
    }

    @Override
    public List<TracingRecordEnity> getTracingRecordList(List<Map<String, String>> tracingMapList) {
        List<TracingRecordEnity> tracingRecordEnityList = new ArrayList<>();
        if (tracingMapList != null && tracingMapList.size() > 0) {
            for (Map<String, String> map : tracingMapList) {
                String trackingNumber = map.get("trackingNumber");
                String carrier = map.get("carrier");
                TracingRecordEnity tracingRecordEnity = shipmentInfoLocalDao.getShipmentTracingRecord(trackingNumber, carrier);
                if (tracingRecordEnity != null) {
                    tracingRecordEnityList.add(tracingRecordEnity);
                }
            }
        }
        return tracingRecordEnityList;
    }

    @Override
    public List<RecentSaleNumEnity> geRecentMskuSaleNum(String stratTime, String endTime, String shopId, String msku) {
        Result result = mskuService.getShopById(shopId);
        String sellerId = null;
        if (result.getCode() == 200) {
            Map map = (Map) result.getData();
            sellerId = (String) map.get("sellerNo");
        }
        if (endTime != null) {
            Date date = DateUtil.convertStrToDate(endTime, DateUtil.DEFAULT_SHORT_DATE_FORMAT);
            date = DateUtil.addDate("dd", 1, date);
            endTime = DateUtil.convertDateToStr(date, DateUtil.DEFAULT_SHORT_DATE_FORMAT);
        }
        List<RecentSaleNumEnity> recentSaleNumEnityList = shipmentInfoLocalDao.getMskuSaleNumCond(stratTime, endTime, sellerId, msku);
        return recentSaleNumEnityList;
    }

    @Override
    public List<RecentSaleNumEnity> getPreSevenDaySaleNum(String mskuId, String shopId) {
        Result result = mskuService.getShopById(shopId);
        String sellerId = null;
        if (result.getCode() == 200) {
            Map map = (Map) result.getData();
            sellerId = (String) map.get("sellerNo");
        }
        List<RecentSaleNumEnity> list = shipmentInfoLocalDao.getPreSevenDaySaleNum(mskuId, sellerId);
        return list;
    }

    @Override
    public void updateSignTime(String trackingNumber, String carrier, String fnsku) {
        String signTime = Time.getCurrentTime();
        Gson gson = new Gson();
        shipmentInfoLocalDao.updateSignTime(trackingNumber, carrier, signTime, "Success");
        List<RemoveOrderDetailEnity> removeOrderDetailEnityList=shipmentInfoLocalDao.getRemoveOrderDetailEnityList(trackingNumber);
        for(RemoveOrderDetailEnity removeOrderDetailEnity:removeOrderDetailEnityList){
            redisTemplate.opsForList().leftPush("removeOrderInfo",gson.toJson(removeOrderDetailEnity));
        }
    }

    @Override
    public Map<String, Integer> getRemoveOrderInfoBatch(List<Map> parameterMapList) {
        Result shopResult = mskuService.getShop(null);
        Map<String, Integer> shipmap = new HashMap();
        Map<String, String> shopMap = new HashMap<>();
        Map<String, String> sellerMap = new HashMap<>();
        if (shopResult.getCode() == 200) {
            Map map = (Map) shopResult.getData();
            if (map != null) {
                List shopList = (List) map.get("storeInfoList");
                if (shopList != null) {
                    for (Object shopObject : shopList) {
                        Map shop = (Map) shopObject;
                        shopMap.put((String) shop.get("shopId"), (String) shop.get("sellerNo"));
                        sellerMap.put((String) shop.get("sellerNo"), (String) shop.get("shopId"));
                    }
                }
            }
        }
        if (parameterMapList != null) {
            for (Map map : parameterMapList) {
                String shopId = (String) map.get("shopId");
                String returnApplyId = (String) map.get("returnApplyId");
                List<String> removeOrderIdList = (List<String>) map.get("removeOrderIdList");
                for (String removeOrderId : removeOrderIdList) {
                    List<TransferShipmentEnity> transferShipmentEnityList = shipmentInfoLocalDao.getShipmentNum(removeOrderId);
                    List<TransferSignEnity> transferSignEnityList = shipmentInfoLocalDao.getSignNum(removeOrderId);
                    for (TransferShipmentEnity transferShipmentEnity : transferShipmentEnityList) {
                        String fnsku = transferShipmentEnity.getFnsku();
                        String shop = sellerMap.get(transferShipmentEnity.getSellerId());
                        String id = fnsku + shop + returnApplyId;
                        if (map.get(id) == null) {
                            shipmap.put(id + "out", transferShipmentEnity.getShippedQuantity());
                        } else {
                            shipmap.put(id + "out", transferShipmentEnity.getShippedQuantity() + shipmap.get(id + "out"));
                        }
                    }
                    for (TransferSignEnity transferShipmentEnity : transferSignEnityList) {
                        String msku = transferShipmentEnity.getMsku();
                        String shop = sellerMap.get(transferShipmentEnity.getSellerId());
                        String id = msku + shop + returnApplyId;
                        if (map.get(id) == null) {
                            if (transferShipmentEnity.getSignNum() != null) {
                                shipmap.put(id + "in", transferShipmentEnity.getSignNum());
                            }
                        } else {
                            if (transferShipmentEnity.getSignNum() != null) {
                                shipmap.put(id + "in", transferShipmentEnity.getSignNum() + shipmap.get(id + "in"));
                            }
                        }
                    }
                }
            }
        }
        return shipmap;
    }


    @Override
    public List<ShipmentTransferEnity> getShipmentTransferInfo(List<Map> mapList) {
        List<ShipmentTransferEnity> shipmentTransferEnityList = new ArrayList<>();
        for (Map map : mapList) {
            String shipmentId = (String) map.get("shipmentId");
            String sellerId = (String) map.get("sellerId");
            List<String> tracingIds = shipmentInfoLocalDao.getTracingId(shipmentId, sellerId);
            for (String tracingId : tracingIds) {
                Map<String, Object> shipMap = shipmentInfoLocalDao.getSignTime(tracingId);
                if (shipMap == null) {
                    continue;
                }
                String carrier = (String) shipMap.get("carrier");
                ShipmentTransferEnity shipmentTransferEnity = new ShipmentTransferEnity();
                shipmentTransferEnity.setTracingId(tracingId);
                shipmentTransferEnity.setCarrier(carrier);
                if (shipMap.get("signTime") != null) {
                    Date signTime = (Date) shipMap.get("signTime");
                    shipmentTransferEnity.setSignTime(DateUtil.convertDateToStr(signTime, DateUtil.DATETIME_FORMAT));
                }
                shipmentTransferEnityList.add(shipmentTransferEnity);
            }
        }
        return shipmentTransferEnityList;
    }

    @Override
    public void updateWaybillSignTime(String trackingNumber, String wayBillId) {
        String signTime = Time.getCurrentTime();
        shipmentInfoLocalDao.updateTime(trackingNumber, signTime, "Success");
        Map map = shipmentInfoLocalDao.getShipmentAndSellerId(trackingNumber);
        if (map != null) {
            String sellerId = (String) map.get("sellerId");
            String shipmentId = (String) map.get("shipmentId");
            List<String> tracingIdList = shipmentInfoLocalDao.getTracingId(shipmentId, sellerId);
            String tracingIds = "";
            for (String tracingId : tracingIdList) {
                tracingIds += "'" + tracingId + "'" + ",";
            }
            if (tracingIds.endsWith(",")) {
                int index = tracingIds.lastIndexOf(",");
                tracingIds = tracingIds.substring(0, index);
            }
            if (StringUtils.isNotEmpty(tracingIds)) {
                List<String> tracing = shipmentInfoLocalDao.getByIds(tracingIds);
                if (tracing.size() <= 0) {
                    String[] waybillIds = new String[]{wayBillId};
                    replenishmentService.updateStatus(waybillIds);
                }
            }
        }
    }
}
