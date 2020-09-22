package com.wisrc.order.webapp.service;

import com.wisrc.order.webapp.entity.OrderBasicInfoEntity;
import com.wisrc.order.webapp.utils.Result;
import com.wisrc.order.webapp.vo.ProductAndWareHouseVo;

import java.util.LinkedHashMap;

public interface OrderBasisInfoService {

    void addOrder(OrderBasicInfoEntity orderBasicInfoEntity);

    OrderBasicInfoEntity getOrderInfo(String orderId);

    Result deleteOrderById(String orderId, String userId);

    Result activeOrder(String orderId, int ifSend, String userId);

    ProductAndWareHouseVo getProAndWareHouse(String shopId, String mskuId);

    LinkedHashMap getOrder(String pageNum, String pageSize, String orderId, String originalOrderId, String platId, String shopId, String createTime, String exceptTypeCd, String mskuId, String mskuName, int statusCd, String countryCd) throws Exception;
}
