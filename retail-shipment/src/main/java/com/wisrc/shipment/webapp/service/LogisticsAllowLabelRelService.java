package com.wisrc.shipment.webapp.service;

import com.wisrc.shipment.webapp.entity.LogisticsAllowLabelRelEntity;

public interface LogisticsAllowLabelRelService {
    /**
     * 新增报价标签
     *
     * @param ele
     */
    void add(LogisticsAllowLabelRelEntity ele);

    void delete(String offerId);

    void physicalDeleteLable(String offerId);
}
