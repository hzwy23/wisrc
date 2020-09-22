package com.wisrc.warehouse.webapp.service.impl;

import com.wisrc.warehouse.webapp.service.WarehouseOutEnterTypeService;
import com.wisrc.warehouse.webapp.dao.WarehouseOutEnterTypeDao;
import com.wisrc.warehouse.webapp.entity.WarehouseOutEnterTypeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WarehouseOutEnterTypeServiceImpl implements WarehouseOutEnterTypeService {
    @Autowired
    private WarehouseOutEnterTypeDao warehouseOutEnterTypeDao;

    @Override
    public List<WarehouseOutEnterTypeEntity> getWarehouseOutEnterType() {
        return warehouseOutEnterTypeDao.getWarehouseOutEnterType();
    }
}
