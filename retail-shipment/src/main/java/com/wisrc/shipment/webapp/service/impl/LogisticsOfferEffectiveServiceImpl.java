package com.wisrc.shipment.webapp.service.impl;

import com.wisrc.shipment.webapp.service.LogisticsOfferEffectiveService;
import com.wisrc.shipment.webapp.dao.LogisticsOfferEffectiveDao;
import com.wisrc.shipment.webapp.entity.LogisticsOfferEffectiveEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogisticsOfferEffectiveServiceImpl implements LogisticsOfferEffectiveService {
    @Autowired
    private LogisticsOfferEffectiveDao logisticsOfferEffectiveDao;

    @Override
    public void add(LogisticsOfferEffectiveEntity ele) {
        logisticsOfferEffectiveDao.add(ele);
    }

    @Override
    public void delete(String offerId) {
        logisticsOfferEffectiveDao.delete(offerId);
    }

    @Override
    public void physicalDeleteEffect(String offerId) {
        logisticsOfferEffectiveDao.phisycalDeleteByOffId(offerId);
    }
}
