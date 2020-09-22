package com.wisrc.order.webapp.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wisrc.order.webapp.dao.*;
import com.wisrc.order.webapp.entity.*;
import com.wisrc.order.webapp.service.ExceptOrderService;
import com.wisrc.order.webapp.service.externalService.BasicService;
import com.wisrc.order.webapp.service.externalService.CodeService;
import com.wisrc.order.webapp.service.externalService.ShipmentService;
import com.wisrc.order.webapp.utils.PageData;
import com.wisrc.order.webapp.utils.ReflectUtil;
import com.wisrc.order.webapp.utils.Result;
import com.wisrc.order.webapp.utils.Time;
import com.wisrc.order.webapp.vo.ExceptOrderVO;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class ExceptOrderServiceImpl implements ExceptOrderService {
    @Autowired
    private ExceptOrderDao exceptOrderDao;
    @Autowired
    private OrderBasicInfoDao saleOrderInfoDao;
    @Autowired
    private OrderCustomerInfoDao saleCustomerInfoDao;
    @Autowired
    private OrderLogisticsInfoDao saleLogisticsInfoDao;
    @Autowired
    private OrderCommodityInfoDao saleOrderCommodityInfoDao;
    @Autowired
    private OrderRemarkInfoDao orderRemarkInfoDao;
    @Autowired
    private OrderLabelInfoDao orderLabelInfoDao;
    @Autowired
    private OrderStatusAttrDao orderStatusAttrDao;
    @Autowired
    private LogInfoDao logInfoDao;
    @Autowired
    private BasicService basicService;
    @Autowired
    private CodeService codeService;
    @Autowired
    private ShipmentService shipmentService;
    @Autowired
    private OrderBasicInfoDao orderBasicInfoDao;
    public LinkedHashMap getExceptOrderByCond(int num, int size, String orderId, String originalOrderId, String platId, String shopId, String commodityId, String commodityName, String createStartTime, String createEndTime, String label) {
        PageHelper.startPage(num, size);
        List<ExceptOrderVO> list = exceptOrderDao.getExceptOrderByCond(orderId, originalOrderId, platId, shopId, commodityId, commodityName, createStartTime, createEndTime, label);
        List labelList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            List<OrderRemarkInfoEntity> remarkInfoEntities = orderRemarkInfoDao.getByOrderId(list.get(i).getOrderId());
            List<OrderLabelInfo> orderLabelInfoList = orderLabelInfoDao.getById(list.get(i).getOrderId());
            list.get(i).setOrderLabelInfoList(orderLabelInfoList);
            list.get(i).setRemarkInfoEntityList(remarkInfoEntities);
            if (label != "" && label != null) {
                for (OrderLabelInfo labelInfo : orderLabelInfoList) {
                    labelList.add(labelInfo.getExceptTypeCd());
                }
                if (!labelList.contains(label)) {
                    list.remove(list.get(i));
                    i--;
                }
            }
        }
        PageInfo pageInfo = new PageInfo(list);
        return PageData.pack(pageInfo.getTotal(), pageInfo.getSize(), "ExceptOrderVOList", list);
    }

    @Override
    public LinkedHashMap getAllOrder(String orderId, String originalOrderId, String platId, String shopId, String commodityId, String commodityName, String createStartTime, String createEndTime, String label) {
        List<ExceptOrderVO> list = exceptOrderDao.getExceptOrderByCond(orderId, originalOrderId, platId, shopId, commodityId, commodityName, createStartTime, createEndTime, label);
        List labelList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            List<OrderRemarkInfoEntity> remarkInfoEntities = orderRemarkInfoDao.getByOrderId(list.get(i).getOrderId());
            List<OrderLabelInfo> orderLabelInfoList = orderLabelInfoDao.getById(list.get(i).getOrderId());
            list.get(i).setOrderLabelInfoList(orderLabelInfoList);
            list.get(i).setRemarkInfoEntityList(remarkInfoEntities);
            if (label != "" && label != null) {
                for (OrderLabelInfo labelInfo : orderLabelInfoList) {
                    labelList.add(labelInfo.getExceptTypeCd());
                }
                if (!labelList.contains(label)) {
                    list.remove(list.get(i));
                    i--;
                }
            }
        }
        return PageData.pack(list.size(), 1, "ExceptOrderVOList", list);
    }


    @Override
    public void update(OrderCustomerInfoEntity entity, String userId) {
        Map<String,String> countryMap=new HashMap<>();
        Result result=codeService.getCountryName();
        if(result.getCode()==200){
            List<Map> mapList= (List<Map>) result.getData();
            if(mapList!=null){
                for(Map map:mapList){
                    String countryCd= (String) map.get("countryCd");
                    String countryName= (String) map.get("countryName");
                    countryMap.put(countryCd,countryName);
                }
            }
        }
        Map<String, String> filedMap = new HashMap<>();
        filedMap.put("customerId", "客户ID");
        filedMap.put("consignee", "收货人");
        filedMap.put("zipCode", "邮编");
        filedMap.put("contactOne", "联系方式1");
        filedMap.put("contactTwo", "联系方式2");
        filedMap.put("countryCd", "收货国家");
        filedMap.put("provinceName", "省份");
        filedMap.put("cityName", "城市");
        filedMap.put("detailsAddr", "地址");
        filedMap.put("buyerMessage", "买家留言");
        OrderCustomerInfoEntity orderCustomerInfoEntity = saleCustomerInfoDao.findAllById(entity.getOrderId());
        saleCustomerInfoDao.update(entity);
        String time = Time.getCurrentDateTime();
        UpdateDetailEnity updateDetailEnity = new UpdateDetailEnity();
        updateDetailEnity.setUpdateTime(time);
        updateDetailEnity.setUpdateUser(userId);
        updateDetailEnity.setOrderId(entity.getOrderId());
        String updateDetail = "";
        Map<String,Object> changeMap=ReflectUtil.compareAttributeValue(orderCustomerInfoEntity,entity);
        if(changeMap.size()>0){
            for(String filed:changeMap.keySet()){
                Map valueMap= (Map) changeMap.get(filed);
                String oldValue="";
                String newValue="";
                if(valueMap.get("oldValue")==null){
                    oldValue="";
                }
                else {
                    oldValue=valueMap.get("oldValue")+"";
                }
                if(valueMap.get("newValue")==null){
                    newValue="";
                }
                else {
                    newValue=valueMap.get("newValue")+"";
                }
                if("countryCd".equals(filed)&& StringUtils.isNotEmpty(oldValue)){
                    updateDetail+="    ["+filedMap.get(filed)+"]"+" 由:"+countryMap.get(oldValue)+"  修改为: "+countryMap.get(newValue);
                    continue;
                }
                updateDetail+="    ["+filedMap.get(filed)+"]"+" 由:"+oldValue+"  修改为: "+newValue;
            }
            updateDetailEnity.setUpdateDetail(updateDetail);
            logInfoDao.insert(updateDetailEnity);
        }
    }

    @Override
    public void update(OrderLogisticsInfo entity, String userId) {
        Result shipmentResult=basicService.getShipMentInfo(1);
        Map<String,String> shipmentMap=new HashMap();
        Map<String,String> channelMap=new HashMap<>();
        if(shipmentResult.getCode()==200){
            Map map= (Map) shipmentResult.getData();
            List<Map> shipList= (List<Map>) map.get("shipmentEnterpriseEntityList");
            if(shipList!=null){
                for(Map shipMap:shipList){
                    String shipmentId= (String) shipMap.get("shipmentId");
                    String shipmentName=(String) shipMap.get("shipmentName");
                    shipmentMap.put(shipmentId,shipmentName);
                }
            }
        }
        Result channelResult=shipmentService.getAllChannleName();
        if(channelResult.getCode()==200){
            List<Map> mapList= (List<Map>) channelResult.getData();
            if(mapList!=null){
                for(Map map:mapList){
                    String offerId= (String) map.get("offerId");
                    String channleName= (String) map.get("channelName");
                    channelMap.put(offerId,channleName);
                }
            }
        }
        OrderLogisticsInfo orderLogisticsInfo = saleLogisticsInfoDao.findAllById(entity.getOrderId());
        Map<String, String> filedMap = new HashMap<>();
        filedMap.put("deliveryChannelCd", "买家自选物流渠道");
        filedMap.put("offerId", "发货物流渠道");
        filedMap.put("logisticsId", "物流单号");
        filedMap.put("logisticsCost", "物流费用");
        filedMap.put("weight", "重量");
        filedMap.put("deliveryDate", "发货时间");
        filedMap.put("deliveryRemark", "发货备注");
        filedMap.put("shipMentId","物流商");
        saleLogisticsInfoDao.update(entity);
        String time = Time.getCurrentDateTime();
        UpdateDetailEnity updateDetailEnity = new UpdateDetailEnity();
        updateDetailEnity.setUpdateTime(time);
        updateDetailEnity.setUpdateUser(userId);
        updateDetailEnity.setOrderId(entity.getOrderId());
        String updateDetail = "";
        Map<String,Object> changeMap=ReflectUtil.compareAttributeValue(orderLogisticsInfo,entity);
        if(changeMap.size()>0){
            for(String filed:changeMap.keySet()){
                Map valueMap= (Map) changeMap.get(filed);
                String oldValue="";
                String newValue="";
                if(valueMap.get("oldValue")==null){
                    oldValue="";
                }
                else {
                    oldValue=valueMap.get("oldValue")+"";
                }
                if(valueMap.get("newValue")==null){
                    newValue="";
                }
                else {
                    newValue=valueMap.get("newValue")+"";
                }
                if("shipMentId".equals(filed)&&StringUtils.isNotEmpty(oldValue)){
                    updateDetail+="    ["+filedMap.get(filed)+"]"+" 由:"+shipmentMap.get(oldValue)+"  修改为: "+shipmentMap.get(newValue);
                    continue;
                }
                if("deliveryChannelCd".equals(filed)&&StringUtils.isNotEmpty(oldValue)){
                    updateDetail+="    ["+filedMap.get(filed)+"]"+" 由:"+channelMap.get(oldValue)+"  修改为: "+channelMap.get(newValue);
                    continue;
                }
                if("offerId".equals(filed)&&StringUtils.isNotEmpty(oldValue)){
                    updateDetail+="    ["+filedMap.get(filed)+"]"+" 由:"+channelMap.get(oldValue)+"  修改为: "+channelMap.get(newValue);
                    continue;
                }
                updateDetail+="    ["+filedMap.get(filed)+"]"+" 由:"+oldValue+"  修改为: "+newValue;
            }
            updateDetailEnity.setUpdateDetail(updateDetail);
            logInfoDao.insert(updateDetailEnity);
        }
    }

    @Override
    public void update(List<OrderCommodityInfoEntity> voList, String userId) {
        Map<String, String> filedMap = new HashMap<>();
        filedMap.put("attributeDesc", "属性");
        filedMap.put("unitPrice", "单价");
        filedMap.put("unitPriceCurrency", "单价币种");
        filedMap.put("quantity", "数量");
        filedMap.put("warehouseId", "仓库");
        filedMap.put("statusCd", "商品状态");
        String time = Time.getCurrentDateTime();
        for (OrderCommodityInfoEntity entity : voList) {
            OrderCommodityInfoEntity orderCommodityInfoEntity = saleOrderCommodityInfoDao.getByUUid(entity.getUuid());
            saleOrderCommodityInfoDao.update(entity);
            Map<String, Object> changeMap = ReflectUtil.compareAttributeValue(orderCommodityInfoEntity, entity);
            String updateDetail = "";
            if (changeMap.size() > 0) {
                for (String filed : changeMap.keySet()) {
                    Map valueMap = (Map) changeMap.get(filed);
                    updateDetail += "  [" + filedMap.get(filed) + "]" + " 由:" + valueMap.get("oldValue") + "  修改为: " + valueMap.get("newValue");
                    String orderId=null;
                    UpdateDetailEnity updateDetailEnity=new UpdateDetailEnity();
                    updateDetailEnity.setUpdateTime(time);
                    updateDetailEnity.setUpdateUser(userId);
                    updateDetailEnity.setOrderId(orderId);
                    updateDetailEnity.setUpdateDetail(updateDetail);
                    String oldValue="";
                    String newValue="";
                    if(valueMap.get("oldValue")==null){
                        oldValue="";
                    }
                    else {
                        oldValue=valueMap.get("oldValue")+"";
                    }
                    if(valueMap.get("newValue")==null){
                        newValue="";
                    }
                    else {
                        newValue=valueMap.get("newValue")+"";
                    }
                    updateDetail+="    ["+filedMap.get(filed)+"]"+" 由:"+oldValue+"  修改为: "+newValue;
                    logInfoDao.insert(updateDetailEnity);
                }
            }
        }
    }

    @Override
    public void changeStatus(String orderId, int statusCd, String userId) {
        List<OrderStatusAttr> orderStatusAttrList = orderStatusAttrDao.findAll();
        OrderBasicInfoEntity basicInfoEntity = orderBasicInfoDao.findById(orderId);
        Map<Integer, String> statusMap = new HashMap();
        for (OrderStatusAttr orderStatusAttr : orderStatusAttrList) {
            statusMap.put(orderStatusAttr.getStatusCd(), orderStatusAttr.getStatusName());
        }
        saleOrderInfoDao.changeStatus(orderId, statusCd);
        String time = Time.getCurrentDateTime();
        UpdateDetailEnity updateDetailEnity = new UpdateDetailEnity();
        updateDetailEnity.setUpdateTime(time);
        updateDetailEnity.setUpdateUser(userId);
        updateDetailEnity.setOrderId(orderId);
        String updateDetail = "[订单状态]" + "由: " + statusMap.get(basicInfoEntity.getStatusCd()) + " 修改为: " + statusMap.get(statusCd);
        updateDetailEnity.setUpdateDetail(updateDetail);
        logInfoDao.insert(updateDetailEnity);
    }
}
