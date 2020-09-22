package com.wisrc.warehouse.webapp.service.impl;

import com.wisrc.warehouse.webapp.dao.EnterWarehouseTypeDao;
import com.wisrc.warehouse.webapp.entity.EnterWarehouseTypeEntity;
import com.wisrc.warehouse.webapp.service.EnterWarehouseTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnterWarehouseTypeServiceImpl implements EnterWarehouseTypeService {
    @Autowired
    private EnterWarehouseTypeDao enterWarehouseTypeDao;

    @Override
    public void addEnter(EnterWarehouseTypeEntity entity) {
        enterWarehouseTypeDao.add(entity);
    }

    @Override
    public List<EnterWarehouseTypeEntity> getList() {
        List<EnterWarehouseTypeEntity> list = enterWarehouseTypeDao.select();
        return list;
    }

    @Override
    public void update(EnterWarehouseTypeEntity entity) {
        enterWarehouseTypeDao.update(entity);
    }


    /**
     * 配置入库类型状态
     *
     * @param enterTypeCd
     * @param action      <code>enable</code>表示启用，<code>disable</code>表示禁用，其他会抛出{@link IllegalArgumentException}
     */
    @Override
    public void configStatus(int enterTypeCd, String action) {
        switch (action) {
            case "enable":
                enterWarehouseTypeDao.configStatus(enterTypeCd, 0);
                break;
            case "disable":
                enterWarehouseTypeDao.configStatus(enterTypeCd, 1);
                break;
            default:
                throw new IllegalArgumentException("非法的action状态");
        }
    }
}
