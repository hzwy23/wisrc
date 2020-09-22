package com.wisrc.warehouse.webapp.vo;

import com.wisrc.warehouse.webapp.entity.EnterWarehouseListEntity;
import com.wisrc.warehouse.webapp.entity.EnterWarehouseRemarkEntity;
import com.wisrc.warehouse.webapp.entity.HandmadeEnterWarehouseBillEntity;

import java.util.List;

public class AddEnterWarehouseBillVO {
    private HandmadeEnterWarehouseBillEntity billEntity;
    private EnterWarehouseRemarkEntity remarkEntity;
    private List<EnterWarehouseListEntity> list;

    public HandmadeEnterWarehouseBillEntity getBillEntity() {
        return billEntity;
    }

    public void setBillEntity(HandmadeEnterWarehouseBillEntity billEntity) {
        this.billEntity = billEntity;
    }

    public EnterWarehouseRemarkEntity getRemarkEntity() {
        return remarkEntity;
    }

    public void setRemarkEntity(EnterWarehouseRemarkEntity remarkEntity) {
        this.remarkEntity = remarkEntity;
    }

    public List<EnterWarehouseListEntity> getList() {
        return list;
    }

    public void setList(List<EnterWarehouseListEntity> list) {
        this.list = list;
    }
}
