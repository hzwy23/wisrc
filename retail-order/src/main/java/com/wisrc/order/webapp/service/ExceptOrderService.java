package com.wisrc.order.webapp.service;

import com.wisrc.order.webapp.entity.OrderCommodityInfoEntity;
import com.wisrc.order.webapp.entity.OrderCustomerInfoEntity;
import com.wisrc.order.webapp.entity.OrderLogisticsInfo;

import java.util.LinkedHashMap;
import java.util.List;

public interface ExceptOrderService {
    //分页模糊查询异常订单
    LinkedHashMap getExceptOrderByCond(int num, int size, String orderId, String originalOrderId, String platId, String shopId, String commodityId, String commodityName, String createStartTime, String createEndTime, String label);

    //模糊查询全部订单
    LinkedHashMap getAllOrder(String orderId, String originalOrderId, String platId, String shopId, String commodityId, String commodityName, String createStartTime, String createEndTime, String label);

    //修改订单
    //void update(OrderInfoDetailVO vo, String userId) throws Exception;

    //标记已发货/作废订单
    void changeStatus(String orderId, int statusCd, String userId);

    void update(OrderCustomerInfoEntity entity, String userId);

    void update(OrderLogisticsInfo entity, String userId);

    void update(List<OrderCommodityInfoEntity> voList, String userId);
}
