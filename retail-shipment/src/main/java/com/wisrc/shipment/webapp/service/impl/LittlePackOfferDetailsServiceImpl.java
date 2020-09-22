package com.wisrc.shipment.webapp.service.impl;

import com.wisrc.shipment.webapp.service.LittlePackOfferDetailsService;
import com.wisrc.shipment.webapp.dao.LittlePackOfferDetailsDao;
import com.wisrc.shipment.webapp.entity.LittlePackOfferDetailsEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LittlePackOfferDetailsServiceImpl implements LittlePackOfferDetailsService {

    @Autowired
    private LittlePackOfferDetailsDao littleDao;

    public void saveLittle(LittlePackOfferDetailsEntity littlePack) {
        littleDao.saveLittlePack(littlePack);
    }

    @Override
    public void delete(String offerId) {
        littleDao.deleteLittlePack(offerId);
    }

    @Override
    public void physicalDeleteDetail(String offerId) {
        littleDao.physicalDelete(offerId);
    }
}
