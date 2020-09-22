package com.wisrc.shipment.webapp.service.impl;

import com.wisrc.shipment.webapp.service.ReturnStatusCodeService;
import com.wisrc.shipment.webapp.dao.ReturnStatusWarehouseDao;
import com.wisrc.shipment.webapp.entity.ReturnWarehouseStatusEnity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReturnStatusCodeServiceImpl implements ReturnStatusCodeService {
    @Autowired
    private ReturnStatusWarehouseDao returnStatusWarehouseDao;

    @Override
    public List<ReturnWarehouseStatusEnity> getAllReturnStatus() {
        return returnStatusWarehouseDao.ggetAllReturnStatus();
    }
}
