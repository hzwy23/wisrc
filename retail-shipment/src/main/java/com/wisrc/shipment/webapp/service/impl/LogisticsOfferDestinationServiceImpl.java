package com.wisrc.shipment.webapp.service.impl;

import com.wisrc.shipment.webapp.service.LogisicsOfferDestinationService;
import com.wisrc.shipment.webapp.dao.LogisticsOfferDestinationDao;
import com.wisrc.shipment.webapp.entity.LogisticsOfferDestinationEnity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogisticsOfferDestinationServiceImpl implements LogisicsOfferDestinationService {
    @Autowired
    private LogisticsOfferDestinationDao logisticOfferDestinationDao;

    @Override
    public void add(LogisticsOfferDestinationEnity dest) {
        logisticOfferDestinationDao.add(dest);
    }

    @Override
    public void delete(String offerId) {
        logisticOfferDestinationDao.delete(offerId);
    }

    @Override
    public void physicalDeleteDest(String offerId) {
        logisticOfferDestinationDao.physicalDelete(offerId);
    }
}
