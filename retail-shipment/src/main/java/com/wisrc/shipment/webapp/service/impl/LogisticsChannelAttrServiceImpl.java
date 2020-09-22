package com.wisrc.shipment.webapp.service.impl;

import com.wisrc.shipment.webapp.service.LogisticsChannelAttrService;
import com.wisrc.shipment.webapp.dao.LogisticsChannelAttrDao;
import com.wisrc.shipment.webapp.entity.LogisticsChannelAttrEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogisticsChannelAttrServiceImpl implements LogisticsChannelAttrService {
    @Autowired
    private LogisticsChannelAttrDao logisticsChannelAttrDao;

    @Override
    public List<LogisticsChannelAttrEntity> findList() {
        return logisticsChannelAttrDao.findList();
    }
}
