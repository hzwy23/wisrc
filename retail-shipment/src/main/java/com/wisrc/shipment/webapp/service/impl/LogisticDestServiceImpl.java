package com.wisrc.shipment.webapp.service.impl;

import com.wisrc.shipment.webapp.service.LogisticDestService;
import com.wisrc.shipment.webapp.dao.DestinationDao;
import com.wisrc.shipment.webapp.entity.FbaDestinationEnity;
import com.wisrc.shipment.webapp.entity.LittlePacketDestEnity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogisticDestServiceImpl implements LogisticDestService {
    @Autowired
    private DestinationDao destinationDao;

    @Override
    public List<FbaDestinationEnity> getAllDest(String countryCd) {
        return destinationDao.getAllFbaDest(countryCd);
    }

    @Override
    public List<LittlePacketDestEnity> getAllLittleDest() {
        return destinationDao.getAllLittleDest();
    }
}
