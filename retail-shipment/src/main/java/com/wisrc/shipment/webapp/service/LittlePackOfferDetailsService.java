package com.wisrc.shipment.webapp.service;

import com.wisrc.shipment.webapp.entity.LittlePackOfferDetailsEntity;

public interface LittlePackOfferDetailsService {

    /**
     * 新增物流报价的同时向小包报价信息表增加数据
     *
     * @param littlePack
     */
    void saveLittle(LittlePackOfferDetailsEntity littlePack);

    /**
     * 根据外键ID逻辑删除小包报价信息表数据
     *
     * @param offerId
     */
    void delete(String offerId);

    void physicalDeleteDetail(String offerId);
}
