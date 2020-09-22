package com.wisrc.warehouse.webapp.vo;

import com.wisrc.warehouse.webapp.entity.EnterWarehouseListEntity;
import com.wisrc.warehouse.webapp.entity.EnterWarehouseRemarkEntity;

import java.util.List;

public class SelectEnterWarehouseBillVO {

    private SelectEnterBillVO vo;
    private List<EnterWarehouseRemarkEntity> list;
    private List<EnterWarehouseListEntity> entityList;

    public SelectEnterBillVO getVo() {
        return vo;
    }

    public void setVo(SelectEnterBillVO vo) {
        this.vo = vo;
    }

    public List<EnterWarehouseRemarkEntity> getList() {
        return list;
    }

    public void setList(List<EnterWarehouseRemarkEntity> list) {
        this.list = list;
    }

    public List<EnterWarehouseListEntity> getEntityList() {
        return entityList;
    }

    public void setEntityList(List<EnterWarehouseListEntity> entityList) {
        this.entityList = entityList;
    }
}
