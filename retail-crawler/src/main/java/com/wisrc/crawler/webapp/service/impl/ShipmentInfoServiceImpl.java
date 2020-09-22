package com.wisrc.crawler.webapp.service.impl;

import com.google.gson.Gson;
import com.wisrc.crawler.basic.ConfigProperties;
import com.wisrc.crawler.webapp.dao.*;
import com.wisrc.crawler.webapp.entity.MskuInfoEnity;
import com.wisrc.crawler.webapp.entity.MskuSaleNumEnity;
import com.wisrc.crawler.webapp.entity.RemoveOrderInfoEnity;
import com.wisrc.crawler.webapp.entity.ShipmentInfoEnity;
import com.wisrc.crawler.webapp.service.ExternalService.MskuService;
import com.wisrc.crawler.webapp.service.ExternalService.ReplenishmentService;
import com.wisrc.crawler.webapp.service.ExternalService.WarehouseService;
import com.wisrc.crawler.webapp.service.ShipmentInfoService;
import com.wisrc.crawler.webapp.utils.DateUtil;
import com.wisrc.crawler.webapp.utils.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ShipmentInfoServiceImpl implements ShipmentInfoService {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ConfigProperties configProperties;
    @Autowired
    private ShipmentInfoDao shipmentInfoDao;
    @Autowired
    private MskuStockDao mskuStockDao;
    @Autowired
    private TracingRecordDao tracingRecordDao;
    @Autowired
    private MskuService mskuService;
    @Autowired
    private WarehouseService warehouseService;
    @Autowired
    private ReplenishmentService replenishmentService;
    @Autowired
    private MskuSaleDao mskuSaleDao;
    @Autowired
    private RemoveOrderDao removeOrderDao;
    protected static final Logger logger = LoggerFactory.getLogger(ShipmentInfoServiceImpl.class);

    @Override
    public Map getShipmentTracingRecord(String shipmentId, String shipmentType) {
        if (shipmentId != null) {
            shipmentId = shipmentId.trim();
        }
        Map<String, String> parameterMap = new HashMap<>();
        parameterMap.put("carrier", shipmentType);
        parameterMap.put("trackingNumber", shipmentId);
        Map resultMap = null;
        try {
            resultMap = restTemplate.getForObject(configProperties.getTrackingUrl(), Map.class, parameterMap);
        } catch (Exception e) {
            logger.info("获取物流记录异常"+":"+shipmentId+"-"+shipmentType+"|"+e.getMessage());
        }
        if (resultMap == null) {
            return null;
        }
        Map map = new HashMap();
        map.put("trackingNumber", null);
        map.put("status", null);
        map.put("trackingInfo", null);
        map.put("signTime", null);
        map.put("deliveryDate", null);
        map.put("signName", null);
        map.put("pickupDate", null);
        map.put("onTheWayDay", null);
        map.put("expectDeliveryDate", null);
        int errno = (int) resultMap.get("errno");
        if (0 != errno) {
            return map;
        }
        Map tracingRecordMap = (Map) resultMap.get("data");
        if (tracingRecordMap == null) {
            return map;
        }
        map.put("trackingNumber", tracingRecordMap.get("trackingNumber"));
        map.put("status", tracingRecordMap.get("status"));
        map.put("trackingInfo", tracingRecordMap.get("trackingInfo"));
        map.put("signTime", tracingRecordMap.get("signTime"));
        map.put("deliveryDate", tracingRecordMap.get("deliveryDate"));
        map.put("signName", tracingRecordMap.get("signName"));
        map.put("pickupDate", tracingRecordMap.get("pickupDate"));
        map.put("onTheWayDay", tracingRecordMap.get("onTheWayDay"));
        map.put("expectDeliveryDate", tracingRecordMap.get("expectDeliveryDate"));
        List tracingRecordMapList = new ArrayList();
        Gson gson = new Gson();
        if (map != null && map.get("trackingNumber") != null) {
            map.put("carrier", shipmentType);
            tracingRecordDao.deleteRecord((String) map.get("trackingNumber"));
            tracingRecordDao.insert(map);
            tracingRecordMapList.add(map);
            replenishmentService.updateWaybill(gson.toJson(tracingRecordMapList));
        }
        return map;
    }

    @Override
    public RemoveOrderInfoEnity getRemoveOrderInfo(String removeOrderId, String sellerId) {
        Map<String, String> parameterMap = new HashMap<>();
        parameterMap.put("sellerId", sellerId);
        parameterMap.put("orderId", removeOrderId);
        Map removeMap = null;
        try {
            removeMap = restTemplate.getForObject(configProperties.getRemoveOrderInfoUrl(), Map.class, parameterMap);
        } catch (Exception e) {
            logger.info("获取移除订单订单号异常"+":"+removeOrderId+"-"+sellerId+"|"+e.getMessage());
        }
        RemoveOrderInfoEnity removeOrderInfoEnity = new RemoveOrderInfoEnity();
        if (removeMap == null) {
            return null;
        }
        int errno = (int) removeMap.get("errno");
        if (0 != errno) {
            return null;
        }
        Map removeOrderMap = (Map) removeMap.get("data");
        if (removeOrderMap == null) {
            return removeOrderInfoEnity;
        }
        if (removeOrderMap.get("requestDate") != null) {
            removeOrderInfoEnity.setCreateTime(DateUtil.convertTime((String) removeOrderMap.get("requestDate")));
        }
        removeOrderInfoEnity.setOrderId((String) removeOrderMap.get("orderId"));
        removeOrderInfoEnity.setOrderStatus((String) removeOrderMap.get("orderStatus"));
        removeOrderInfoEnity.setSellerId((String) removeOrderMap.get("sellerId"));
        List list = (List) removeOrderMap.get("removeOrderShipmentInfoList");
        List mskuList= (List<Map>) removeOrderMap.get("removeOrderMskuInfoList");
        removeOrderInfoEnity.setRemoveOrderShipmentInfoList(list);
        removeOrderInfoEnity.setRemoveMskuInfoList(mskuList);
        if (removeOrderInfoEnity != null && removeOrderInfoEnity.getOrderId() != null) {
            List removeOrderDetailList = removeOrderInfoEnity.getRemoveOrderShipmentInfoList();
            List removeMskuList=removeOrderInfoEnity.getRemoveMskuInfoList();
            if(removeMskuList!=null){
                removeOrderDao.deleteMskuInfo(removeOrderInfoEnity.getSellerId(), removeOrderInfoEnity.getOrderId());
                for(Object object:removeMskuList){
                    Map map = (Map) object;
                    map.put("sellerId", removeOrderInfoEnity.getSellerId());
                    map.put("orderId", removeOrderInfoEnity.getOrderId());
                    removeOrderInfoEnity.setSellerId(removeOrderInfoEnity.getSellerId());
                    removeOrderInfoEnity.setOrderId(removeOrderInfoEnity.getOrderId());
                    removeOrderDao.insertMsku(map);
                }
            }
            if (removeOrderDetailList != null) {
                removeOrderDao.deleteDetail(removeOrderInfoEnity.getSellerId(), removeOrderInfoEnity.getOrderId());
                removeOrderDao.deleteInfo(removeOrderInfoEnity.getSellerId(), removeOrderInfoEnity.getOrderId());
                removeOrderDao.insertRemoveOrderInfo(removeOrderInfoEnity);
                for (Object object : removeOrderDetailList) {
                    Map map = (Map) object;
                    map.put("sellerId", removeOrderInfoEnity.getSellerId());
                    map.put("orderId", removeOrderInfoEnity.getOrderId());
                    String shipmentDate = (String) map.get("shipmentDate");
                    String trackingNumber=(String) map.get("trackingNumber");
                    if(trackingNumber!=null&&trackingNumber.length()>60){
                        continue;
                    }
                    map.put("shipmentDate", DateUtil.convertTime(shipmentDate));
                    removeOrderDao.insertDetail(map);
                }
            }
        }
        return removeOrderInfoEnity;
    }


    @Override
    public ShipmentInfoEnity getShipmentInfo(String shipmentId, String sellerId) {
        Map<String, String> parameterMap = new HashMap<>();
        if (shipmentId != null) {
            shipmentId = shipmentId.trim();
        }
        List<ShipmentInfoEnity> shipmentInfoEnityList = new ArrayList<>();
        parameterMap.put("sellerId", sellerId);
        parameterMap.put("shipmentId", shipmentId);
        Map resultMap = null;
        try {
            resultMap = restTemplate.getForObject(configProperties.getShipmentInfoUrl(), Map.class, parameterMap);
        } catch (Exception e) {
            logger.info("获取货件信息异常"+":"+shipmentId+"-"+sellerId+"|"+e.getMessage());
        }
        if (resultMap == null) {
            return null;
        }
        ShipmentInfoEnity shipmentInfoEnity = new ShipmentInfoEnity();
        int errno = (int) resultMap.get("errno");
        if (0 != errno) {
            return shipmentInfoEnity;
        }
        Map shipmentInfoMap = (Map) resultMap.get("data");
        if (shipmentInfoMap == null) {
            return shipmentInfoEnity;
        }
        shipmentInfoEnity.setDestinationFulfillmentCenterId((String) shipmentInfoMap.get("destinationFulfillmentCenterId"));
        shipmentInfoEnity.setShipmentName((String) shipmentInfoMap.get("shipmentName"));
        shipmentInfoEnity.setCarrierName((String) shipmentInfoMap.get("carrierName"));
        shipmentInfoEnity.setAmountValue((Double) shipmentInfoMap.get("amountValue"));
        shipmentInfoEnity.setShipmentType((String) shipmentInfoMap.get("shipmentType"));
        List<String> tracingIds = (List<String>) shipmentInfoMap.get("trackingIds");
        shipmentInfoEnity.setTracingIds(tracingIds);
        List list = (List) shipmentInfoMap.get("shipmentInfoItemList");
        shipmentInfoEnity.setShipmentInfoItemList(list);
        shipmentInfoEnity.setShipmentStatus((String) shipmentInfoMap.get("shipmentStatus"));
        shipmentInfoEnity.setSellerId(sellerId);
        shipmentInfoEnity.setShipmentId(shipmentId);
        shipmentInfoDao.delete(shipmentId, sellerId);
        shipmentInfoDao.insertShipmentInfo(shipmentInfoEnity);
        if (tracingIds != null && tracingIds.size() > 0) {
            shipmentInfoDao.deleteTransfer(shipmentId, sellerId);
            for (String tracingId : tracingIds) {
                shipmentInfoDao.insertTransferRel(shipmentId, sellerId, tracingId);
                Map map = new HashMap();
                map.put("trackingNumber", tracingId);
                map.put("carrier", shipmentInfoEnity.getCarrierName());
                if (shipmentInfoEnity.getCarrierName() != null) {
                    tracingRecordDao.deleteRecord(tracingId);
                    tracingRecordDao.insert(map);
                }
            }
        }
        if (list != null && list.size() > 0) {
            shipmentInfoDao.deleteDetail(shipmentId, sellerId);
            for (Object object : list) {
                Map map = (Map) object;
                map.put("shipmentId", shipmentId);
                map.put("sellerId", sellerId);
                shipmentInfoDao.insertShipmentDetail(map);
            }
        }
        shipmentInfoEnityList.add(shipmentInfoEnity);
        Gson gson = new Gson();
        replenishmentService.batchUpdateSignNum(gson.toJson(shipmentInfoEnityList));
        return shipmentInfoEnity;
    }

    @Override
    public MskuInfoEnity getShipmentMskuInfo(String mskuId, String sellerId) {
        Map<String, String> parameterMap = new HashMap<>();
        parameterMap.put("msku", mskuId);
        parameterMap.put("sellerId", sellerId);
        Map resultMap = null;
        try {
            resultMap = restTemplate.getForObject(configProperties.getMskuInfoUrl(), Map.class, parameterMap);
        } catch (Exception e) {

        }
        if (resultMap == null) {
            return null;
        }
        int errno = (int) resultMap.get("errno");
        if (0 != errno) {
            return null;
        }
        Map mskuInfoMap = (Map) resultMap.get("data");
        if (mskuInfoMap == null) {
            return null;
        }
        MskuInfoEnity mskuInfoEnity = new MskuInfoEnity();
        mskuInfoEnity.setAsin((String) mskuInfoMap.get("asin"));
        mskuInfoEnity.setFnsku((String) mskuInfoMap.get("fnsku"));
        mskuInfoEnity.setMskuCnName((String) mskuInfoMap.get("productName"));
        Object price=mskuInfoMap.get("price");
        if(price!=null){
            if(price instanceof Integer){
                String thePrice=(Integer)mskuInfoMap.get("price")+"";
                mskuInfoEnity.setSalePrice(Double.parseDouble(thePrice));
            }
            else {
                mskuInfoEnity.setSalePrice((Double) mskuInfoMap.get("price"));
            }
        }
        mskuInfoEnity.setDeliveryType((String) mskuInfoMap.get("deliveryType"));
        mskuInfoEnity.setMsku((String) mskuInfoMap.get("msku"));
        return mskuInfoEnity;
    }

    @Override
    public MskuSaleNumEnity getMskuSaleNum(String mskuId, String sellerId) {
        Map<String, String> parameterMap = new HashMap<>();
        parameterMap.put("sellerId", sellerId);
        parameterMap.put("msku", mskuId);
        Map resultMap = null;
        MskuSaleNumEnity mskuSaleNumEnity = new MskuSaleNumEnity();
        try {
            resultMap = restTemplate.getForObject(configProperties.getSaleRecordUrl(), Map.class, parameterMap);
        } catch (Exception e) {

        }
        if (resultMap == null) {
            return null;
        }
        int errno = (int) resultMap.get("errno");
        if (0 != errno) {
            return mskuSaleNumEnity;
        }
        Map saleRecordMap = (Map) resultMap.get("data");
        if (saleRecordMap == null) {
            return mskuSaleNumEnity;
        }
        mskuSaleNumEnity.setLastNum((Integer) saleRecordMap.get("oneDaysAgoSales"));
        mskuSaleNumEnity.setLastTwoNum((Integer) saleRecordMap.get("twoDaysAgoSales"));
        mskuSaleNumEnity.setLastThreeNum((Integer) saleRecordMap.get("threeDaysAgoSales"));
        mskuSaleNumEnity.setMsku((String) saleRecordMap.get("msku"));
        mskuSaleNumEnity.setSellerId((String) saleRecordMap.get("sellerId"));
        mskuSaleNumEnity.setDataDt((String) saleRecordMap.get("dataDt"));
        List<MskuSaleNumEnity> mskuSaleNumEnityList = new ArrayList<>();
        if (mskuSaleNumEnity != null && mskuSaleNumEnity.getMsku() != null) {
            mskuSaleDao.delete(mskuSaleNumEnity.getMsku(), mskuSaleNumEnity.getSellerId(), mskuSaleNumEnity.getDataDt());
            mskuSaleDao.insert(mskuSaleNumEnity);
            mskuSaleNumEnityList.add(mskuSaleNumEnity);
        }
        Gson gson = new Gson();
        mskuService.updateMskuSaleStock(gson.toJson(mskuSaleNumEnityList));
        return mskuSaleNumEnity;
    }

    @Override
    public Map getMskuStockInfo(String mskuId, String sellerId) {
        Result shopResult = mskuService.getShop(null);
        Map sellShopMap = new HashMap<>();
        if (shopResult.getCode() == 200) {
            Map map = (Map) shopResult.getData();
            if (map != null) {
                List shopList = (List) map.get("storeInfoList");
                if (shopList != null) {
                    for (Object shopObject : shopList) {
                        Map shop = (Map) shopObject;
                        sellShopMap.put((String) shop.get("sellerNo"), (String) shop.get("shopId"));
                    }
                }
            }
        }
        Map<String, String> parameterMap = new HashMap<>();
        parameterMap.put("sellerId", sellerId);
        parameterMap.put("msku", mskuId);
        Map resultMap = null;
        try {
            resultMap = restTemplate.getForObject(configProperties.getMskuStockUrl(), Map.class, parameterMap);
        } catch (Exception e) {
            logger.info("获取库存信息异常"+":"+mskuId+"-"+sellerId+"|"+e.getMessage());
            return null;
        }
        if (resultMap == null) {
            return null;
        }
        Map map = new HashMap();
        map.put("eableStockNum", null);
        map.put("unableStockNum", null);
        map.put("reservedCustomerorders", null);
        map.put("reservedFcTransfers", null);
        map.put("reservedFcProcessing", null);
        map.put("sellerId", null);
        map.put("dataDt", null);
        map.put("msku", null);
        int errno = (int) resultMap.get("errno");
        if (0 != errno) {
            return map;
        }
        Map mskuStockMap = (Map) resultMap.get("data");
        if (mskuStockMap == null) {
            return map;
        }
//      可用库存
        map.put("eableStockNum", mskuStockMap.get("afnFulfillableQuantity"));
//      不可用库存
        map.put("unableStockNum", mskuStockMap.get("afnUnsellableQuantity"));
//      买家订单预留
        map.put("reservedCustomerorders", mskuStockMap.get("reservedCustomerorders"));
//      各仓库调拨预留
        map.put("reservedFcTransfers", mskuStockMap.get("reservedFcTransfers"));
//      仓库接收处理中预留
        map.put("reservedFcProcessing", mskuStockMap.get("reservedFcProcessing"));
        map.put("sellerId", mskuStockMap.get("sellerId"));
        map.put("msku", mskuStockMap.get("msku"));
        map.put("dataDt", mskuStockMap.get("dataDt"));
        if (mskuStockMap == null || mskuStockMap.get("msku") == null) {
            return map;
        }
        mskuStockDao.delete((String) map.get("msku"), (String) map.get("sellerId"));
        mskuStockDao.insert(mskuStockMap);
        List list = new ArrayList();
        map.put("shopId", sellShopMap.get(mskuStockMap.get("sellerId")));
        list.add(map);
        Gson gson = new Gson();
        warehouseService.updateFbaStockDetail(gson.toJson(list));
        return map;
    }

    @Override
    public List<MskuSaleNumEnity> getBatchMskuSaleNum(List list) {
        Map resultMap = null;
        List<MskuSaleNumEnity> mskuSaleNumEnityList = new ArrayList<>();
        resultMap = restTemplate.postForObject(configProperties.getSaleRecordBatchUrl(), list, Map.class);
        System.out.println(resultMap);
        if (resultMap == null) {
            return null;
        }
        int errno = (int) resultMap.get("errno");
        if (0 != errno) {
            return null;
        }
        List<Map> saleRecordMapList = (List<Map>) resultMap.get("data");
        if (saleRecordMapList != null && saleRecordMapList.size() > 0) {
            for (Map saleRecordMap : saleRecordMapList) {
                if (saleRecordMap == null) {
                    continue;
                }
                MskuSaleNumEnity mskuSaleNumEnity = new MskuSaleNumEnity();
                mskuSaleNumEnity.setLastNum((Integer) saleRecordMap.get("oneDaysAgoSales"));
                mskuSaleNumEnity.setLastTwoNum((Integer) saleRecordMap.get("twoDaysAgoSales"));
                mskuSaleNumEnity.setLastThreeNum((Integer) saleRecordMap.get("threeDaysAgoSales"));
                mskuSaleNumEnity.setMsku((String) saleRecordMap.get("msku"));
                mskuSaleNumEnity.setSellerId((String) saleRecordMap.get("sellerId"));
                mskuSaleNumEnity.setDataDt((String) saleRecordMap.get("dataDt"));
                mskuSaleNumEnityList.add(mskuSaleNumEnity);
                mskuSaleDao.delete(mskuSaleNumEnity.getMsku(), mskuSaleNumEnity.getSellerId(), mskuSaleNumEnity.getDataDt());
                mskuSaleDao.insert(mskuSaleNumEnity);
            }
            return mskuSaleNumEnityList;
        }
        return mskuSaleNumEnityList;
    }

    @Override
    public MskuInfoEnity getShipmentMskuInfoByShopIdAndMskuId(String mskuId, String shopId) {
        Result result = mskuService.getShopById(shopId);
        String sellerId = null;
        if (result.getCode() == 200) {
            Map map = (Map) result.getData();
            if (map != null) {
                sellerId = (String) map.get("sellerNo");
            }
        }
        Map<String, String> parameterMap = new HashMap<>();
        parameterMap.put("msku", mskuId);
        parameterMap.put("sellerId", sellerId);
        Map resultMap = null;
        try {
            resultMap = restTemplate.getForObject(configProperties.getMskuInfoUrl(), Map.class, parameterMap);
        } catch (Exception e) {

        }
        if (resultMap == null) {
            return null;
        }
        int errno = (int) resultMap.get("errno");
        if (0 != errno) {
            return null;
        }
        Map mskuInfoMap = (Map) resultMap.get("data");
        if (mskuInfoMap == null) {
            return null;
        }
        MskuInfoEnity mskuInfoEnity = new MskuInfoEnity();
        mskuInfoEnity.setAsin((String) mskuInfoMap.get("asin"));
        mskuInfoEnity.setFnsku((String) mskuInfoMap.get("fnsku"));
        mskuInfoEnity.setMskuCnName((String) mskuInfoMap.get("productName"));
        mskuInfoEnity.setSalePrice((Double) mskuInfoMap.get("price"));
        mskuInfoEnity.setDeliveryType((String) mskuInfoMap.get("deliveryType"));
        mskuInfoEnity.setMsku((String) mskuInfoMap.get("msku"));
        return mskuInfoEnity;
    }

    @Override
    public Map getRemoveTracingRecord(String trackingNumber, String carrier) {
        Map<String, String> parameterMap = new HashMap<>();
        parameterMap.put("carrier", carrier);
        parameterMap.put("trackingNumber", trackingNumber);
        Map resultMap = null;
        try {
            resultMap = restTemplate.getForObject(configProperties.getTrackingUrl(), Map.class, parameterMap);
        } catch (Exception e) {
            logger.info("获取移除订单物流记录异常"+":"+trackingNumber+"-"+carrier+"|"+e.getMessage());
        }
        if (resultMap == null) {
            return null;
        }
        Map map = new HashMap();
        map.put("trackingNumber", null);
        map.put("status", null);
        map.put("trackingInfo", null);
        map.put("signTime", null);
        map.put("deliveryDate", null);
        map.put("signName", null);
        map.put("pickupDate", null);
        map.put("onTheWayDay", null);
        map.put("expectDeliveryDate", null);
        int errno = (int) resultMap.get("errno");
        if (0 != errno) {
            return map;
        }
        Map tracingRecordMap = (Map) resultMap.get("data");
        if (tracingRecordMap == null) {
            return map;
        }
        map.put("trackingNumber", tracingRecordMap.get("trackingNumber"));
        map.put("status", tracingRecordMap.get("status"));
        map.put("trackingInfo", tracingRecordMap.get("trackingInfo"));
        map.put("signTime", tracingRecordMap.get("signTime"));
        map.put("deliveryDate", tracingRecordMap.get("deliveryDate"));
        map.put("signName", tracingRecordMap.get("signName"));
        map.put("pickupDate", tracingRecordMap.get("pickupDate"));
        map.put("onTheWayDay", tracingRecordMap.get("onTheWayDay"));
        map.put("expectDeliveryDate", tracingRecordMap.get("expectDeliveryDate"));
        if (map != null && map.get("trackingNumber") != null) {
            map.put("carrier", carrier);
            tracingRecordDao.deleteRecord((String) map.get("trackingNumber"));
            tracingRecordDao.insert(map);
        }
        return map;
    }

    @Override
    public Map getSingleShipmentTracingRecord(String trackingNumber, String carrier) {
        if (trackingNumber != null) {
            trackingNumber = trackingNumber.trim();
        }
        Map<String, String> parameterMap = new HashMap<>();
        parameterMap.put("carrier", carrier);
        parameterMap.put("trackingNumber", trackingNumber);
        Map resultMap = null;
        try {
            resultMap = restTemplate.getForObject(configProperties.getTrackingUrl(), Map.class, parameterMap);
        } catch (Exception e) {

        }
        if (resultMap == null) {
            return null;
        }
        Map map = new HashMap();
        map.put("trackingNumber", null);
        map.put("status", null);
        map.put("trackingInfo", null);
        map.put("signTime", null);
        map.put("deliveryDate", null);
        map.put("signName", null);
        map.put("pickupDate", null);
        map.put("onTheWayDay", null);
        map.put("expectDeliveryDate", null);
        int errno = (int) resultMap.get("errno");
        if (0 != errno) {
            return map;
        }
        Map tracingRecordMap = (Map) resultMap.get("data");
        if (tracingRecordMap == null) {
            return map;
        }
        map.put("trackingNumber", tracingRecordMap.get("trackingNumber"));
        map.put("status", tracingRecordMap.get("status"));
        if (tracingRecordMap.get("status") == null) {
            map.put("status", "");
        }
        map.put("trackingInfo", tracingRecordMap.get("trackingInfo"));
        map.put("signTime", tracingRecordMap.get("signTime"));
        map.put("deliveryDate", tracingRecordMap.get("deliveryDate"));
        map.put("signName", tracingRecordMap.get("signName"));
        map.put("pickupDate", tracingRecordMap.get("pickupDate"));
        map.put("onTheWayDay", tracingRecordMap.get("onTheWayDay"));
        map.put("expectDeliveryDate", tracingRecordMap.get("expectDeliveryDate"));
        map.put("carrier", carrier);
        tracingRecordDao.deleteRecord((String) map.get("trackingNumber"));
        tracingRecordDao.insert(map);
        return map;
    }

    @Override
    public Map getSingelMskuStockInfo(String fnsku, String sellerId) {
        Result shopResult = mskuService.getShop(null);
        Map sellShopMap = new HashMap<>();
        if (shopResult.getCode() == 200) {
            Map map = (Map) shopResult.getData();
            if (map != null) {
                List shopList = (List) map.get("storeInfoList");
                if (shopList != null) {
                    for (Object shopObject : shopList) {
                        Map shop = (Map) shopObject;
                        sellShopMap.put((String) shop.get("sellerNo"), (String) shop.get("shopId"));
                    }
                }
            }
        }
        Map<String, String> parameterMap = new HashMap<>();
        parameterMap.put("sellerId", sellerId);
        parameterMap.put("fnsku", fnsku);
        Map resultMap = null;
        try {
            resultMap = restTemplate.getForObject(configProperties.getMskuStockUrl(), Map.class, parameterMap);
        } catch (Exception e) {
            return null;
        }
        if (resultMap == null) {
            return null;
        }
        Map map = new HashMap();
        map.put("eableStockNum", null);
        map.put("unableStockNum", null);
        map.put("reservedCustomerorders", null);
        map.put("reservedFcTransfers", null);
        map.put("reservedFcProcessing", null);
        map.put("sellerId", null);
        map.put("dataDt", null);
        map.put("fnsku", null);
        int errno = (int) resultMap.get("errno");
        if (0 != errno) {
            return map;
        }
        Map mskuStockMap = (Map) resultMap.get("data");
        if (mskuStockMap == null) {
            return map;
        }
//      可用库存
        map.put("eableStockNum", mskuStockMap.get("afnFulfillableQuantity"));
//      不可用库存
        map.put("unableStockNum", mskuStockMap.get("afnUnsellableQuantity"));
//      买家订单预留
        map.put("reservedCustomerorders", mskuStockMap.get("reservedCustomerorders"));
//      各仓库调拨预留
        map.put("reservedFcTransfers", mskuStockMap.get("reservedFcTransfers"));
//      仓库接收处理中预留
        map.put("reservedFcProcessing", mskuStockMap.get("reservedFcProcessing"));
        map.put("sellerId", mskuStockMap.get("sellerId"));
        map.put("fnsku", mskuStockMap.get("fnsku"));
        map.put("dataDt", mskuStockMap.get("dataDt"));
        mskuStockDao.delete((String) map.get("fnsku"), (String) map.get("sellerId"));
        mskuStockDao.insert(mskuStockMap);
        return map;
    }

    @Override
    public ShipmentInfoEnity getSingelShipmentInfo(String batchNumber, String sellerId) {
        Map<String, String> parameterMap = new HashMap<>();
        parameterMap.put("sellerId", sellerId);
        parameterMap.put("shipmentId", batchNumber);
        Map resultMap = null;
        try {
            resultMap = restTemplate.getForObject(configProperties.getShipmentInfoUrl(), Map.class, parameterMap);
        } catch (Exception e) {

        }
        if (resultMap == null) {
            return null;
        }
        ShipmentInfoEnity shipmentInfoEnity = new ShipmentInfoEnity();
        int errno = (int) resultMap.get("errno");
        if (0 != errno) {
            return shipmentInfoEnity;
        }
        Map shipmentInfoMap = (Map) resultMap.get("data");
        if (shipmentInfoMap == null) {
            return shipmentInfoEnity;
        }
        shipmentInfoEnity.setDestinationFulfillmentCenterId((String) shipmentInfoMap.get("destinationFulfillmentCenterId"));
        shipmentInfoEnity.setShipmentName((String) shipmentInfoMap.get("shipmentName"));
        shipmentInfoEnity.setCarrierName((String) shipmentInfoMap.get("carrierName"));
        shipmentInfoEnity.setAmountValue((Double) shipmentInfoMap.get("amountValue"));
        shipmentInfoEnity.setShipmentType((String) shipmentInfoMap.get("shipmentType"));
        List<String> tracingIds = (List<String>) shipmentInfoMap.get("trackingIds");
        shipmentInfoEnity.setTracingIds(tracingIds);
        List list = (List) shipmentInfoMap.get("shipmentInfoItemList");
        shipmentInfoEnity.setShipmentInfoItemList(list);
        shipmentInfoEnity.setShipmentStatus((String) shipmentInfoMap.get("shipmentStatus"));
        shipmentInfoEnity.setSellerId(sellerId);
        shipmentInfoEnity.setShipmentId(batchNumber);
        shipmentInfoDao.delete(batchNumber, sellerId);
        shipmentInfoDao.insertShipmentInfo(shipmentInfoEnity);
        if (tracingIds != null && tracingIds.size() > 0) {
            shipmentInfoDao.deleteTransfer(batchNumber, sellerId);
            for (String tracingId : tracingIds) {
                shipmentInfoDao.insertTransferRel(batchNumber, sellerId, tracingId);
                Map map = new HashMap();
                map.put("trackingNumber", tracingId);
                map.put("carrier", shipmentInfoEnity.getCarrierName());
                if (shipmentInfoEnity.getCarrierName() != null) {
                    tracingRecordDao.deleteRecord(tracingId);
                    tracingRecordDao.insert(map);
                }
            }
        }
        if (list != null && list.size() > 0) {
            shipmentInfoDao.deleteDetail(batchNumber, sellerId);
            for (Object object : list) {
                Map map = (Map) object;
                map.put("shipmentId", batchNumber);
                map.put("sellerId", sellerId);
                shipmentInfoDao.insertShipmentDetail(map);
            }
        }
        return shipmentInfoEnity;
    }

    @Override
    public String getShelveData(String asin) {
        Map<String, String> parameterMap = new HashMap<>();
        parameterMap.put("asin", asin);
        Map resultMap = null;
        try {
            resultMap = restTemplate.getForObject(configProperties.getShelveInfoUrl(), Map.class, parameterMap);
        } catch (Exception e) {

        }
        if (resultMap == null) {
            return null;
        }
        int errno = (int) resultMap.get("errno");
        if (0 != errno) {
            return null;
        }
        return (String) resultMap.get("data");
    }
}
