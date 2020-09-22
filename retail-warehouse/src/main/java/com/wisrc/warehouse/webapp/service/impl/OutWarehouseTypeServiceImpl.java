package com.wisrc.warehouse.webapp.service.impl;

import com.wisrc.warehouse.webapp.dao.OutWarehouseTypeDao;
import com.wisrc.warehouse.webapp.entity.OutWarehouseTypeEntity;
import com.wisrc.warehouse.webapp.service.OutWarehouseTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OutWarehouseTypeServiceImpl implements OutWarehouseTypeService {
    @Autowired
    private OutWarehouseTypeDao outWarehouseTypeDao;

    @Override
    public void addOut(OutWarehouseTypeEntity entity) {
        outWarehouseTypeDao.add(entity);
    }

    @Override
    public List<OutWarehouseTypeEntity> getList() {
        List<OutWarehouseTypeEntity> list = outWarehouseTypeDao.select();
        return list;
    }

    @Override
    public void update(OutWarehouseTypeEntity entity) {
        outWarehouseTypeDao.update(entity);
    }

    /**
     * 配置出库类型状态
     *
     * @param outTypeCd
     * @param action    <code>enable</code>表示启用，<code>disable</code>表示禁用，其他会抛出{@link IllegalArgumentException}
     */
    @Override
    public void configStatus(int outTypeCd, String action) {
        switch (action) {
            case "enable":
                outWarehouseTypeDao.configStatus(outTypeCd, 0);
                break;
            case "disable":
                outWarehouseTypeDao.configStatus(outTypeCd, 1);
                break;
            default:
                throw new IllegalArgumentException("非法的action状态");
        }
    }
}
