package com.wisrc.shipment.webapp.service;

import com.wisrc.shipment.webapp.entity.LogisticsOfferEffectiveEntity;

public interface LogisticsOfferEffectiveService {
    /**
     * 新增物流报价的同时向时效表增加数据
     *
     * @param ele
     */
    void add(LogisticsOfferEffectiveEntity ele);

    /**
     * 根据外键ID逻辑删除时效表数据
     *
     * @param offerId
     */
    void delete(String offerId);

    void physicalDeleteEffect(String offerId);
}
