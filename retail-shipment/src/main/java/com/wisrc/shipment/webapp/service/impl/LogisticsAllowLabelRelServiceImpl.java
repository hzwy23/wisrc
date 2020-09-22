package com.wisrc.shipment.webapp.service.impl;

import com.wisrc.shipment.webapp.service.LogisticsAllowLabelRelService;
import com.wisrc.shipment.webapp.dao.LogisticsAllowLabelRelDao;
import com.wisrc.shipment.webapp.entity.LogisticsAllowLabelRelEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogisticsAllowLabelRelServiceImpl implements LogisticsAllowLabelRelService {
    @Autowired
    private LogisticsAllowLabelRelDao logisticsAllowLabelRelDao;

    @Override
    public void add(LogisticsAllowLabelRelEntity ele) {
        logisticsAllowLabelRelDao.add(ele);
    }

    @Override
    public void delete(String offerId) {
        logisticsAllowLabelRelDao.delete(offerId);
    }

    @Override
    public void physicalDeleteLable(String offerId) {
        logisticsAllowLabelRelDao.physicalDeleteLable(offerId);
    }
}
