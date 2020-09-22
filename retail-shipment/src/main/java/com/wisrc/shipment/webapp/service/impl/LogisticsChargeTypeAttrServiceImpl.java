package com.wisrc.shipment.webapp.service.impl;

import com.wisrc.shipment.webapp.service.LogisticsChargeTypeAttrService;
import com.wisrc.shipment.webapp.dao.LogisticsChargeTypeAttrDao;
import com.wisrc.shipment.webapp.entity.LogisticsChargeTypeAttrEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class LogisticsChargeTypeAttrServiceImpl implements LogisticsChargeTypeAttrService {
    @Autowired
    private LogisticsChargeTypeAttrDao logisticsChargeTypeAttrDao;

    @Override
    public List<LogisticsChargeTypeAttrEntity> findList(int channelTypeCd) {
        return logisticsChargeTypeAttrDao.findList(channelTypeCd);
    }
}
