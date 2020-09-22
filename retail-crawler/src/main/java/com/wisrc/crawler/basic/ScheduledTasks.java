package com.wisrc.crawler.basic;

import com.google.gson.Gson;
import com.wisrc.crawler.webapp.dao.*;
import com.wisrc.crawler.webapp.entity.*;
import com.wisrc.crawler.webapp.service.ExternalService.MskuService;
import com.wisrc.crawler.webapp.service.ExternalService.ReplenishmentService;
import com.wisrc.crawler.webapp.service.ExternalService.ShipmentService;
import com.wisrc.crawler.webapp.service.ExternalService.WarehouseService;
import com.wisrc.crawler.webapp.service.ShipmentInfoService;
import com.wisrc.crawler.webapp.utils.Result;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@PropertySource({"classpath:schedule.properties"})
public class ScheduledTasks {
    protected static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);

    public static int task1 = 0;
    public static int task2 = 0;
    public static int task3 = 0;
    public static int task4 = 0;
    public static int task5 = 0;
    public static int task6 = 0;
    public static int task7 = 0;

    @Autowired
    private ShipmentService shipmentService;

    @Autowired
    private MskuService mskuService;

    @Autowired
    private ReplenishmentService replenishmentService;

    @Autowired
    private ShipmentInfoService shipmentInfoService;

    @Autowired
    private TracingRecordDao tracingRecordDao;

    @Autowired
    private MskuStockDao mskuStockDao;

    @Autowired
    private RemoveOrderDao removeOrderDao;

    @Autowired
    private MskuSaleDao mskuSaleDao;

    @Autowired
    private ShipmentInfoDao shipmentInfoDao;

    @Autowired
    private WarehouseService warehouseService;

    @Autowired
    private ErrorLogDao errorLogDao;

    @Autowired
    private ShipmentInfoLocalDao shipmentInfoLocalDao;

    @Autowired
    private RedisTemplate redisTemplate;


    /*@Scheduled(cron = "0 0 6 * * *")        //每天6点执行
    @Transactional
    @Scheduled(initialDelay = 1000, fixedRate = 5000*600)*/
//    @Scheduled(initialDelay = 1000, fixedRate = 5000*600)
    @Scheduled(cron = "${schedule.shipmentTransferInfo}")
    public void shipmentTransferInfo() {
        logger.info("shipmentTransferInfo开始执行");
        task1 = 1;
        List<Map> applyList = new ArrayList();
        List tracingRecordMapList = new ArrayList();
        Result carrierResult = shipmentService.getAllCarrier(1);
        Map carrierMap = new HashMap();
        if (carrierResult.getCode() == 200) {
            List<Object> objectList = (List<Object>) carrierResult.getData();
            if (objectList != null) {
                for (Object object : objectList) {
                    Map carrier = (Map) object;
                    carrierMap.put(carrier.get("offerId"), carrier.get("logisticsTrackUrl"));
                }
            }
        } else {
            throw new RuntimeException("外部接口调用异常");
        }
        Result logisticResult = replenishmentService.getLogisticOffer();
        if (logisticResult.getCode() == 200) {
            List<Object> objectList = (List<Object>) logisticResult.getData();
            for (Object object : objectList) {
                Map logisticMap = (Map) object;
                Map map = new HashMap();
                map.put("trackingNumber", logisticMap.get("logisticsId"));
                map.put("carrier", logisticMap.get("logisticsTrackUrl"));
                applyList.add(map);
            }
        } else {
            throw new RuntimeException("外部接口调用异常");
        }
        List<Map<String, String>> mapList = shipmentInfoDao.getNoSignTimeTransfer();
        for (Map map : mapList) {
            applyList.add(map);
        }
        for (Map applyMap : applyList) {
            Map tracingRecordMap = shipmentInfoService.getSingleShipmentTracingRecord((String) applyMap.get("trackingNumber"), (String) applyMap.get("carrier"));
            if (tracingRecordMap == null || tracingRecordMap.get("trackingNumber") == null) {
                continue;
            }
            tracingRecordMap.put("carrier", (String) applyMap.get("carrier"));
            tracingRecordMapList.add(tracingRecordMap);
        }
        Gson gson = new Gson();
        int endIdx = 50;
        for(int startIdx = 0; startIdx < tracingRecordMapList.size();){
            if (endIdx > tracingRecordMapList.size()) {
                endIdx = tracingRecordMapList.size();
            }
            List subList = tracingRecordMapList.subList(startIdx, endIdx);
            Result result = replenishmentService.updateWaybill(gson.toJson(subList));
            if (result.getCode() != 200) {
                throw new RuntimeException("外部接口调用异常");
            }
            startIdx = endIdx;
            endIdx = endIdx + 50;
        }
        logger.info("shipmentTransferInfo结束执行");
        task1 = 0;
    }

    //    @Scheduled(cron = "0 0 17 * * *")        //每天17点执行
//    @Transactional
//    @Scheduled(initialDelay = 1000, fixedRate = 5000*600)
    @Scheduled(cron = "${schedule.mskuStockInfo}")
    public void mskuStockInfo() {
        logger.info("mskuStockInfo开始执行");
        task2 = 1;
        List<Map> applyList = new ArrayList();
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
        } else {
            throw new RuntimeException("外部接口调用异常");
        }
        Result mskuResult = mskuService.getMskuInfo(null, null, null, null, 1);
        if (mskuResult.getCode() == 200) {
            Map mskuMap = (Map) mskuResult.getData();
            if (mskuMap != null) {
                List mskuList = (List) mskuMap.get("mskuList");
                if (mskuList != null) {
                    for (Object mskuObject : mskuList) {
                        Map msku = (Map) mskuObject;
                        Map map = new HashMap();
                        String shopId = (String) msku.get("shopId");
                        String sellerId = shopMap.get(shopId);
                        map.put("sellerId", sellerId);
                        map.put("msku", msku.get("mskuId"));
                        map.put("shopId", msku.get("shopId"));
                        map.put("fnsku",msku.get("fnsku"));
                        shopMap.put(sellerId, shopId);
                        applyList.add(map);
                    }
                }
            }
        } else {
            throw new RuntimeException("外部接口调用异常");
        }
        Gson gson = new Gson();
        List list = new ArrayList();
        for (Map applyMap : applyList) {
            Map mskuStockMap = shipmentInfoService.getSingelMskuStockInfo((String) applyMap.get("fnsku"), (String) applyMap.get("sellerId"));
            if (mskuStockMap == null || mskuStockMap.get("fnsku") == null) {
                continue;
            }
            mskuStockMap.put("shopId", (String) applyMap.get("shopId"));
            list.add(mskuStockMap);
        }
        int endIdx = 50;
        for(int startIdx = 0; startIdx < list.size();){
            if (endIdx > list.size()) {
                endIdx = list.size();
            }
            List subList = list.subList(startIdx, endIdx);
            Result result = warehouseService.updateFbaStockDetail(gson.toJson(subList));
            Result result1 = mskuService.updateMskuStock(gson.toJson(subList));
            if (result.getCode() != 200 || result1.getCode() != 200) {
                throw new RuntimeException("外部接口调用异常");
            }
            startIdx = endIdx;
            endIdx = endIdx + 50;
        }
        logger.info("mskuStockInfo结束执行");
        task2 = 0;
    }


    //    @Scheduled(cron = "0 */20 * * * *")        //每天10分钟执行
//    @Transactional
//    @Scheduled(initialDelay = 1000, fixedRate = 5000*600)
    @Scheduled(cron = "${schedule.mskuSaleInfo}")
    public void mskuSaleInfo() {
        logger.info("mskuSaleInfo开始执行");
        List<Map> applyList = new ArrayList();
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
        } else {
            throw new RuntimeException("外部接口调用异常");
        }
        Result mskuResult = mskuService.getMskuInfo(null, null, null, null, 1);
        if (mskuResult.getCode() == 200) {
            Map mskuMap = (Map) mskuResult.getData();
            if (mskuMap != null) {
                List mskuList = (List) mskuMap.get("mskuList");
                if (mskuList != null) {
                    for (Object mskuObject : mskuList) {
                        Map msku = (Map) mskuObject;
                        Map map = new HashMap();
                        String shopId = (String) msku.get("shopId");
                        map.put("sellerId", shopMap.get(shopId));
                        map.put("msku", msku.get("mskuId"));
                        applyList.add(map);
                    }
                }
            }
        } else {
            throw new RuntimeException("外部接口调用异常");
        }
        int number = applyList.size();
        int times = 0;
        if (number % 100 == 0) {
            times = number / 100;
        } else {
            times = number / 100 + 1;
        }
        Gson gson = new Gson();

            Result mskuResult1 = mskuService.getMskuInfo(null, null, null, null, 1);
            if (mskuResult1 != null && mskuResult1.getCode() == 200) {
                // 调用商品管理接口，获取所有的商品信息
                Map mskuNewMap = (Map) mskuResult.getData();
                if (mskuNewMap != null) {
                    // 获取data信息，并从中提出去商品列表信息
                    List mskuList = (List) mskuNewMap.get("mskuList");
                    if (mskuList != null) {
                        List mskulist = new ArrayList();
                        for (Object mskuObject : mskuList) {
                            Map msku = (Map) mskuObject;
                            Map map = new HashMap();
                            String shopId = (String) msku.get("shopId");
                            map.put("sellerId", shopMap.get(shopId));
                            map.put("msku", msku.get("mskuId"));
                            mskulist.add(map);
                        }
                        int endIdx = 10;
                        for(int startIdx = 0; startIdx < mskulist.size();){
                            if (endIdx > mskulist.size()) {
                                endIdx = mskulist.size();
                            }
                            List subList = mskulist.subList(startIdx, endIdx);
                            List<MskuSaleNumEnity> mskuSaleNumEnityList = shipmentInfoService.getBatchMskuSaleNum(subList);
                            if (mskuSaleNumEnityList != null) {
                                Result result = mskuService.updateMskuSaleStock(gson.toJson(mskuSaleNumEnityList));
                                if (result.getCode() != 200) {
                                    throw new RuntimeException("外部接口调用异常");
                                }
                            }
                            startIdx = endIdx;
                            endIdx = endIdx + 10;
                        }
                        logger.info("mskuSaleInfo结束执行");
                    }
                }
            } else {
                throw new RuntimeException("外部接口调用异常");
            }

      /*  Gson gson=new Gson();
        for(Map applyMap:applyList){
            MskuSaleNumEnity mskuSaleNumEnity=shipmentInfoService.getMskuSaleNum((String) applyMap.get("msku"),(String) applyMap.get("sellerId"));
            if(mskuSaleNumEnity==null||mskuSaleNumEnity.getMsku()==null){
                continue;
            }
            mskuSaleDao.insert(mskuSaleNumEnity);
            List list=new ArrayList();
            list.add(mskuSaleNumEnity);
            mskuService.updateMskuSaleStock(gson.toJson(list));
        }*/
    }

    /* @Scheduled(cron = "0 0 3 * * *")        //每天3点执行
     @Transactional*/
//    @Scheduled(initialDelay = 1000, fixedRate = 5000*600)
    @Scheduled(cron = "${schedule.removeOrderInfo}")
    public void removeOrderInfo() {
        logger.info("removeOrderInfo开始执行");
        task3 = 1;
        List<Map> applyList = new ArrayList();
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
        } else {
            throw new RuntimeException("外部接口调用异常");
        }
        Result removeOrderResult = shipmentService.getRemoveOrder();
        if (removeOrderResult.getCode() == 200) {
            List<Object> objectList = (List<Object>) removeOrderResult.getData();
            if (objectList != null) {
                for (Object object : objectList) {
                    Map removeOrder = (Map) object;
                    Map map = new HashMap();
                    map.put("removeOrderId", removeOrder.get("removeOrderId"));
                    String shopId = (String) removeOrder.get("shopId");
//                    map.put("sellerId","A2AH5RH633J7PR");
                    map.put("sellerId", shopMap.get(shopId));
                    applyList.add(map);
                }
            }
        } else {
            throw new RuntimeException("外部接口调用异常");
        }
        for (Map applyMap : applyList) {
            RemoveOrderInfoEnity removeOrderInfoEnity = shipmentInfoService.getRemoveOrderInfo((String) applyMap.get("removeOrderId"), (String) applyMap.get("sellerId"));
        }
        logger.info("removeOrderInfo结束执行");
        task3 = 0;
    }

    //    @Transactional
    /*@Scheduled(cron = "0 0 5 * * *")        //每天7点执行*/
    /* @Scheduled(initialDelay = 1000, fixedRate = 5000*600)*/
//    @Scheduled(initialDelay = 1000, fixedRate = 5000*600)
    @Scheduled(cron = "${schedule.shipmentInfo}")
    public void shipmentInfo() {
        logger.info("shipmentInfo开始执行");
        task4 = 1;
        List<ShipmentInfoEnity> shipmentInfoEnityList = new ArrayList<>();
        Result result = replenishmentService.getShipmentEnity();
        List<Map> shipmentEnityList = null;
        if (result.getCode() == 200) {
            shipmentEnityList = (List<Map>) result.getData();
        } else {
            throw new RuntimeException("外部接口调用异常");
        }
        if (shipmentEnityList != null) {
            for (Map map : shipmentEnityList) {
                ShipmentInfoEnity shipmentInfoEnity = shipmentInfoService.getSingelShipmentInfo((String) map.get("batchNumber"), (String) map.get("sellerId"));
                if (shipmentInfoEnity != null && shipmentInfoEnity.getShipmentId() != null) {
                    shipmentInfoEnityList.add(shipmentInfoEnity);
                }
            }
        }
        Gson gson = new Gson();
        int endIdx = 50;
        for(int startIdx = 0; startIdx < shipmentInfoEnityList.size();){
            if (endIdx > shipmentInfoEnityList.size()) {
                endIdx = shipmentInfoEnityList.size();
            }
            List<ShipmentInfoEnity> subList = shipmentInfoEnityList.subList(startIdx, endIdx);
            Result result1 = replenishmentService.batchUpdateSignNum(gson.toJson(subList));
            if (result1.getCode() != 200) {
                throw new RuntimeException("外部接口调用异常");
            }
            startIdx = endIdx;
            endIdx = endIdx + 50;
        }
        logger.info("shipmentInfo结束执行");
        task4 = 0;
    }

    //    @Transactional
//    @Scheduled(cron = "0 0 6 * * *")
//    @Scheduled(initialDelay = 1000, fixedRate = 5000*600)
    @Scheduled(cron = "${schedule.removeOrderShipment}")
    public void removeOrderShipment() {
        logger.info("removeOrderShipment开始执行");
        task5 = 1;
        Gson gson = new Gson();
        List<RemoveOrderDetailEnity> removeOrderDetailEnityList = removeOrderDao.getNoTransferOrder();
        if (removeOrderDetailEnityList != null) {
            for (RemoveOrderDetailEnity removeOrderDetailEnity : removeOrderDetailEnityList) {
                Map map = shipmentInfoService.getRemoveTracingRecord(removeOrderDetailEnity.getTrackingNumber(), removeOrderDetailEnity.getCarrier());
                if(map==null){
                    continue;
                }
                if (map.get("signTime") != null) {
                    if(redisTemplate.opsForHash().get("removeOrderMark",removeOrderDetailEnity.getTrackingNumber()+removeOrderDetailEnity.getFnsku())!=null){
                        continue;
                    }
                    List<RemoveOrderDetailEnity> removeOrderDetailLists = shipmentInfoLocalDao.getRemoveOrderDetailEnity(removeOrderDetailEnity.getTrackingNumber(),removeOrderDetailEnity.getFnsku());
                    for(RemoveOrderDetailEnity removeOrderDetail:removeOrderDetailLists){
                        removeOrderDetail.setStatus(0);
                        redisTemplate.opsForList().leftPush("removeOrderInfo",gson.toJson(removeOrderDetail));
                    }
                    redisTemplate.opsForHash().put("removeOrderMark",removeOrderDetailEnity.getTrackingNumber()+removeOrderDetailEnity.getFnsku(),"1");
                }
            }
            redisTemplate.delete("removeOrderMark");
        }
        logger.info("removeOrderShipment结束执行");
        task5 = 0;
    }

    //    @Scheduled(cron = "0 */20 * * * *")        //每天10分钟执行
//    @Transactional
//    @Scheduled(initialDelay = 1000, fixedRate = 5000*600)
    @Scheduled(cron = "${schedule.shelveInfo}")
    public void mskuShelve() {
        task6 = 1;
        logger.info("mskuShelve开始执行");
        List<Map> mapList = new ArrayList<>();
        Result mskuResult = mskuService.getUnShelve();
        if (mskuResult.getCode() == 200) {
            List<String> list = (List<String>) mskuResult.getData();
            for (String asin : list) {
                Map map = new HashMap();
                String data = shipmentInfoService.getShelveData(asin);
                if (data != null) {
                    map.put("asin", asin);
                    map.put("shelfDate", data);
                    mapList.add(map);
                }
            }
        } else {
            throw new RuntimeException("外部接口调用异常");
        }
        Gson gson = new Gson();
        int endIdx = 50;
        for(int startIdx = 0; startIdx < mapList.size();){
            if (endIdx > mapList.size()) {
                endIdx = mapList.size();
            }
            List<Map> subList = mapList.subList(startIdx, endIdx);
            Result result = mskuService.updateShelveInfo(gson.toJson(subList));
            if (result.getCode() != 200) {
                throw new RuntimeException("外部接口调用异常");
            }
            startIdx = endIdx;
            endIdx = endIdx + 50;
        }
        logger.info("mskuShelve结束执行");
        task6 = 0;
    }

//    @Scheduled(cron = "${schedule.shipmentTransfer}")
    public void shipmentTransfer() {
        logger.info("shipmentTransfer开始执行");
        task7 = 1;
        List<String> applyList = new ArrayList();
        Result shipmentResult = replenishmentService.getShipment();
        if (shipmentResult.getCode() == 200) {
            List<Map> mapList = (List<Map>) shipmentResult.getData();
            for (Map map : mapList) {
                String waybiilId = (String) map.get("waybillId");
                String batchNumber = (String) map.get("batchNumber");
                List<String> tracingIdList = shipmentInfoLocalDao.getTracingIdByBachNumber(batchNumber);
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
                        applyList.add(waybiilId);
                    }
                }
            }
        } else {
            throw new RuntimeException("外部接口调用异常");
        }
        String[] wayBillIds = new String[applyList.size()];
        Result result = replenishmentService.updateStatus(wayBillIds);
        if (result.getCode() != 200) {
            throw new RuntimeException("外部接口调用异常");
        }
        task7 = 0;
    }

    @Scheduled(cron = "0 */30 * * * *")
//    @Scheduled(initialDelay = 1000, fixedRate = 5000*600)
    public void checkScheduled() {
        List<ErrorLogEnity> errorLogEnityList = errorLogDao.getLogEnity();
        for (ErrorLogEnity errorLogEnity : errorLogEnityList) {
            if (errorLogEnity.getErrorId() == 1) {
                try {
                    if (task1 == 0) {
                        shipmentTransferInfo();
                        errorLogDao.updateLog(0, 1);
                    }
                } catch (Exception e) {

                }
            }
            if (errorLogEnity.getErrorId() == 2) {
                try {
                    if (task2 == 0) {
                        mskuStockInfo();
                        errorLogDao.updateLog(0, 2);
                    }
                } catch (Exception e) {

                }
            }
            if (errorLogEnity.getErrorId() == 3) {
                try {
                    if (task3 == 0) {
                        removeOrderInfo();
                        errorLogDao.updateLog(0, 3);
                    }
                } catch (Exception e) {

                }
            }
            if (errorLogEnity.getErrorId() == 4) {
                try {
                    if (task4 == 0) {
                        shipmentInfo();
                        errorLogDao.updateLog(0, 4);
                    }
                } catch (Exception e) {

                }
            }
            if (errorLogEnity.getErrorId() == 5) {
                try {
                    if (task5 == 0) {
                        removeOrderShipment();
                        errorLogDao.updateLog(0, 5);
                    }
                } catch (Exception e) {

                }
            }
            if (errorLogEnity.getErrorId() == 6) {
                try {
                    if (task6 == 0) {
                        mskuShelve();
                        errorLogDao.updateLog(0, 6);
                    }
                } catch (Exception e) {

                }
            }
        }
    }


}
