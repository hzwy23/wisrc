package com.wisrc.purchase.webapp.service;

import com.wisrc.purchase.webapp.entity.DynamicFieldsAttrEntity;
import com.wisrc.purchase.webapp.entity.OrderProvisionEntity;
import com.wisrc.purchase.webapp.entity.ProvisionMouldEntity;
import com.wisrc.purchase.webapp.vo.orderProvision.OrderProvisionMouldVO;
import com.wisrc.purchase.webapp.vo.orderProvision.OrderProvisionVO;

import java.util.List;

public interface OrderProvisionService {
    /**
     * 查询订单条款信息ById
     *
     * @param orderId
     * @return
     */
    OrderProvisionVO findOrderProvision(String orderId);

    /**
     * 查询条款模板列表
     *
     * @return
     */
    List<ProvisionMouldEntity> findProvisionMould();

    /**
     * 查询条款模板列表ById
     *
     * @return
     */
    OrderProvisionMouldVO findProvisionMouldById(String uuid);

    /**
     * 查询在光标位置插入字段码表信息列表
     *
     * @return
     */
    List<DynamicFieldsAttrEntity> findDynamicFieldsAttr();

    /**
     * 新增订单条款信息
     *
     * @param
     */
    void addOrderProvision(OrderProvisionVO vo);

    /**
     * 新增条款模板
     */
    void addProvisionMould(OrderProvisionMouldVO vo, String userId);

    /**
     * 逻辑删除订单条款信息
     *
     * @param
     */
    void delOrderProvision(OrderProvisionEntity orderProvisionEntity);

    /**
     * 逻辑删除条款模板
     *
     * @param
     */
    void delProvisionMould(ProvisionMouldEntity provisionMouldEntity);


    /**
     * 修改订单条款信息
     *
     * @param
     */
    void updateOrderProvision(OrderProvisionVO vo);

    /**
     * 修改条款模板
     */
    void updateProvisionMould(OrderProvisionMouldVO vo, String userId);
}
