package com.wisrc.warehouse.webapp.service.impl;

import com.wisrc.warehouse.webapp.dao.WarehouseDocumentTypeDao;
import com.wisrc.warehouse.webapp.entity.WarehouseDocumentTypeEntity;
import com.wisrc.warehouse.webapp.service.WarehouseDocumentTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WarehouseDocumentTypeServiceImpl implements WarehouseDocumentTypeService {

    @Autowired
    private WarehouseDocumentTypeDao warehouseDocumentTypeDao;

    @Override
    public List<WarehouseDocumentTypeEntity> findAll() {
        return warehouseDocumentTypeDao.findAll();
    }
}
