package com.wisrc.order.webapp.service;


import com.wisrc.order.webapp.entity.OrderRemarkInfoEntity;
import com.wisrc.order.webapp.utils.Result;
import com.wisrc.order.webapp.vo.SplitOrderInfoListVO;


public interface SaleWaitDeliveryService {
    //待配货订单流转成配货中订单
    void updateWaitStatus(String orderId, OrderRemarkInfoEntity entity, String userId);

    //订单拆分
    Result splitOrder(SplitOrderInfoListVO splitOrderInfoListVO, String userId);


}
