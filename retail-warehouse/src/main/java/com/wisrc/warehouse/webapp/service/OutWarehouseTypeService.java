package com.wisrc.warehouse.webapp.service;

import com.wisrc.warehouse.webapp.entity.OutWarehouseTypeEntity;

import java.util.List;

public interface OutWarehouseTypeService {
    void addOut(OutWarehouseTypeEntity entity);

    List<OutWarehouseTypeEntity> getList();

    void update(OutWarehouseTypeEntity entity);


    /**
     * 配置出库类型状态
     *
     * @param outTypeCd
     * @param action    <code>enable</code>表示启用，<code>disable</code>表示禁用，其他会抛出{@link IllegalArgumentException}
     */
    void configStatus(int outTypeCd, String action);
}
