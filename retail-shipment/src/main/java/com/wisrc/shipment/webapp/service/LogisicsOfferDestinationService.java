package com.wisrc.shipment.webapp.service;

import com.wisrc.shipment.webapp.entity.LogisticsOfferDestinationEnity;

public interface LogisicsOfferDestinationService {
    /**
     * 新增物流报价的同时向目的地增加数据
     *
     * @param dest
     */
    void add(LogisticsOfferDestinationEnity dest);

    /**
     * 根据外键ID逻辑删除目的地表数据
     *
     * @param offerId
     */
    void delete(String offerId);

    void physicalDeleteDest(String offerId);
}
