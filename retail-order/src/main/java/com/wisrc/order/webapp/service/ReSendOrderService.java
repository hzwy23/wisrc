package com.wisrc.order.webapp.service;

import com.wisrc.order.webapp.entity.ReSendOrderEnity;
import com.wisrc.order.webapp.utils.Result;
import com.wisrc.order.webapp.vo.SaleOrderCommodityInfoVO;
import com.wisrc.order.webapp.vo.SendOrderInfoVo;

import java.util.List;

public interface ReSendOrderService {

    /**
     * 批量重发时查询出多个订单对应物流,商品数据等
     *
     * @param orderIDs
     */
    List<SendOrderInfoVo> getAllByIds(String[] orderIDs);

    /**
     * 批量将订单状态类型改为重发状态,新增重发历史记录
     *
     * @param reSendOrderEnity
     * @param userId
     */
    Result reSendOrders(ReSendOrderEnity reSendOrderEnity, String userId);

    /**
     * 订单重发时获取单个商品信息
     *
     * @param orderId
     */
    List<SaleOrderCommodityInfoVO> getAllById(String orderId);
}
