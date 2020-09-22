package com.wisrc.crawler.webapp.controller;

import com.google.gson.Gson;
import com.wisrc.crawler.basic.ScheduledTasks;
import com.wisrc.crawler.webapp.dao.RemoveOrderDao;
import com.wisrc.crawler.webapp.dao.ShipmentInfoLocalDao;
import com.wisrc.crawler.webapp.entity.RemoveOrderDetailEnity;
import com.wisrc.crawler.webapp.entity.RemoveOrderInfoEnity;
import com.wisrc.crawler.webapp.service.ExternalService.MskuService;
import com.wisrc.crawler.webapp.service.ExternalService.ShipmentService;
import com.wisrc.crawler.webapp.service.ExternalService.WarehouseService;
import com.wisrc.crawler.webapp.service.ShipmentInfoService;
import com.wisrc.crawler.webapp.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "定时任务相关")
@RequestMapping(value = "/crawler")
public class OrderManualController {
    protected static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);
    @Autowired
    private ShipmentInfoService shipmentInfoService;
    @Autowired
    private ShipmentService shipmentService;

    @Autowired
    private RemoveOrderDao removeOrderDao;

    @Autowired
    private ShipmentInfoLocalDao shipmentInfoLocalDao;

    @Autowired
    private MskuService mskuService;
    @Autowired
    private WarehouseService warehouseService;

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping(value = "/removeOrderSignTime/manual", method = RequestMethod.GET)
    @ApiOperation(value = "手动启动移除订单任务通过物流获取签收时间(第二步)")
    @ResponseBody
    public Result getRemoveOrderSignTime() {
       Thread thread=new Thread(){
           public void run(){
               try {
                   Gson gson = new Gson();
                   redisTemplate.delete("test");
                   List<RemoveOrderDetailEnity> removeOrderDetailEnityList = removeOrderDao.getNoTransferOrder();
                   int i=1;
                   if (removeOrderDetailEnityList != null) {
                       for (RemoveOrderDetailEnity removeOrderDetailEnity : removeOrderDetailEnityList) {
                           logger.info("第"+i+"次执行"+gson.toJson(removeOrderDetailEnity));
                           Map map = shipmentInfoService.getRemoveTracingRecord(removeOrderDetailEnity.getTrackingNumber(), removeOrderDetailEnity.getCarrier());
                           if(map==null){
                               continue;
                           }
                           if (map.get("signTime") != null) {
                               List<RemoveOrderDetailEnity> removeOrderDetailLists = shipmentInfoLocalDao.getRemoveOrderDetailEnity(removeOrderDetailEnity.getTrackingNumber(),removeOrderDetailEnity.getFnsku());
                               for(RemoveOrderDetailEnity removeOrderDetail:removeOrderDetailLists){
                                   removeOrderDetail.setStatus(0);
                                   redisTemplate.opsForList().leftPush("removeOrderInfo",gson.toJson(removeOrderDetail));
                               }
                           }
                           i++;
                       }
                   }
               }
               catch (Exception e){
                   logger.info(e.getMessage());
               }
           }
       };
       thread.start();
       return  Result.success("手动启动定时成功");
    }


    @RequestMapping(value = "/removeOrderTracing/manual", method = RequestMethod.GET)
    @ApiOperation(value = "手动获取订单里的物流单号(第一步)")
    @ResponseBody
    public Result getRemoveTracingNum() {
        Thread thread=new Thread(){
            public void run(){
                try {
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
                        throw new RuntimeException("外部接口调用异常"+shopResult.getMsg());
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
                        throw new RuntimeException("外部接口调用异常"+removeOrderResult.getMsg());
                    }
                    for (Map applyMap : applyList) {
                        RemoveOrderInfoEnity removeOrderInfoEnity = shipmentInfoService.getRemoveOrderInfo((String) applyMap.get("removeOrderId"), (String) applyMap.get("sellerId"));
                    }
                }
                catch (Exception e){
                    logger.info(e.getMessage());
                }
            }
        };
        thread.start();
        return  Result.success("手动获取订单里的物流单号成功");
    }

    @RequestMapping(value = "/mskuStock/manual", method = RequestMethod.GET)
    @ApiOperation(value = "手动更新库存")
    @ResponseBody
    public Result getMskuStock() {
        Thread thread=new Thread(){
            public void run(){
                try {
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
                        throw new RuntimeException("外部接口调用异常"+shopResult.getMsg());
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
                        throw new RuntimeException("外部接口调用异常"+mskuResult.getMsg());
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
                            throw new RuntimeException("外部接口调用异常"+result.getMsg());
                        }
                        startIdx = endIdx;
                        endIdx = endIdx + 50;
                    }
                }
                catch (Exception e){
                    logger.info(e.getMessage());
                }
            }
        };
        thread.start();
        return  Result.success("手动获取订单里的物流单号成功");
    }
}
