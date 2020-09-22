package com.wisrc.shipment.webapp.service;

import com.wisrc.shipment.webapp.entity.LogisticsChargeTypeAttrEntity;

import java.util.List;

public interface LogisticsChargeTypeAttrService {
    List<LogisticsChargeTypeAttrEntity> findList(int channelTypeCd);
}
