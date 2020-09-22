package com.wisrc.warehouse.webapp.service;

import com.wisrc.warehouse.webapp.entity.EnterWarehouseTypeEntity;

import java.util.List;

public interface EnterWarehouseTypeService {
    void addEnter(EnterWarehouseTypeEntity entity);

    List<EnterWarehouseTypeEntity> getList();

    void update(EnterWarehouseTypeEntity entity);

    /**
     * 配置入库类型状态
     *
     * @param enterTypeCd
     * @param action      <code>enable</code>表示启用，<code>disable</code>表示禁用，其他会抛出{@link IllegalArgumentException}
     */
    void configStatus(int enterTypeCd, String action);
}
