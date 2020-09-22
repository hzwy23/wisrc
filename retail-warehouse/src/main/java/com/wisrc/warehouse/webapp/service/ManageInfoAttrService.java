package com.wisrc.warehouse.webapp.service;

import com.wisrc.warehouse.webapp.entity.WarehouseStatusAttrEntity;
import com.wisrc.warehouse.webapp.entity.WarehouseTypeAttrEntity;

import java.util.List;

public interface ManageInfoAttrService {
    /**
     * 仓库状态码表查询
     */
    List<WarehouseStatusAttrEntity> getStatusAttr();

    /**
     * 仓库类型码表查询
     */
    List<WarehouseTypeAttrEntity> getTypeAttr();
}
