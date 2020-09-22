package com.wisrc.replenishment.webapp.service.impl;

import com.wisrc.replenishment.webapp.dao.FbaReplenishmentPickupAttrDao;
import com.wisrc.replenishment.webapp.entity.FbaReplenishmentPickupAttrEntity;
import com.wisrc.replenishment.webapp.service.FbaReplenishmentPickupAttrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FbaReplenishmentPickupAttrServiceImpl implements FbaReplenishmentPickupAttrService {

    @Autowired
    private FbaReplenishmentPickupAttrDao pickupAttrDao;

    @Override
    public List<FbaReplenishmentPickupAttrEntity> findAllPickupAttr() {
        return pickupAttrDao.findAllPickupAttr();
    }

    @Override
    public FbaReplenishmentPickupAttrEntity findByPickupCd(int pickupTypeCd) {
        return pickupAttrDao.findByPickupCd(pickupTypeCd);
    }
}
