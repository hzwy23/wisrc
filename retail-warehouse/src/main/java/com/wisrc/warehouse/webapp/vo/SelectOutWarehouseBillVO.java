package com.wisrc.warehouse.webapp.vo;

import com.wisrc.warehouse.webapp.entity.OutWarehouseListEntity;
import com.wisrc.warehouse.webapp.entity.OutWarehouseRemarkEntity;

import java.util.List;

public class SelectOutWarehouseBillVO {
    private SelectOutBillVO vo;
    private List<OutWarehouseRemarkEntity> list;
    private List<OutWarehouseListEntity> entityList;

    public SelectOutBillVO getVo() {
        return vo;
    }

    public void setVo(SelectOutBillVO vo) {
        this.vo = vo;
    }

    public List<OutWarehouseRemarkEntity> getList() {
        return list;
    }

    public void setList(List<OutWarehouseRemarkEntity> list) {
        this.list = list;
    }

    public List<OutWarehouseListEntity> getEntityList() {
        return entityList;
    }

    public void setEntityList(List<OutWarehouseListEntity> entityList) {
        this.entityList = entityList;
    }
}
