package com.wisrc.warehouse.webapp.service.impl;

import com.wisrc.warehouse.webapp.dao.ManageInfoAttrDao;
import com.wisrc.warehouse.webapp.entity.WarehouseStatusAttrEntity;
import com.wisrc.warehouse.webapp.entity.WarehouseTypeAttrEntity;
import com.wisrc.warehouse.webapp.service.ManageInfoAttrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManageInfoAttrServiceImpl implements ManageInfoAttrService {
    @Autowired
    private ManageInfoAttrDao manageInfoAttrDao;

    @Override
    public List<WarehouseStatusAttrEntity> getStatusAttr() {
        return manageInfoAttrDao.manageStatusAttr();
    }

    @Override
    public List<WarehouseTypeAttrEntity> getTypeAttr() {
        return manageInfoAttrDao.manageTypeAttr();
    }


}
