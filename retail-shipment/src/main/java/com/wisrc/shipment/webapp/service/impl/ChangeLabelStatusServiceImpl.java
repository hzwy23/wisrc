package com.wisrc.shipment.webapp.service.impl;

import com.wisrc.shipment.webapp.service.ChangeLabelStatusService;
import com.wisrc.shipment.webapp.dao.ChangeLabelStatusDao;
import com.wisrc.shipment.webapp.entity.ChangeLabelStatusEnity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChangeLabelStatusServiceImpl implements ChangeLabelStatusService {

    @Autowired
    private ChangeLabelStatusDao changeLabelStatusDao;

    @Override
    public List<ChangeLabelStatusEnity> getAll() {
        return changeLabelStatusDao.getAll();
    }
}
