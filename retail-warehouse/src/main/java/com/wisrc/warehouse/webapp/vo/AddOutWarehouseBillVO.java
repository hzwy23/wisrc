package com.wisrc.warehouse.webapp.vo;

import com.wisrc.warehouse.webapp.entity.HandmadeOutWarehouseBillEntity;
import com.wisrc.warehouse.webapp.entity.OutWarehouseListEntity;
import com.wisrc.warehouse.webapp.entity.OutWarehouseRemarkEntity;

import java.util.List;

public class AddOutWarehouseBillVO {
    private HandmadeOutWarehouseBillEntity billEntity;
    private OutWarehouseRemarkEntity remarkEntity;
    private List<OutWarehouseListEntity> list;

    public HandmadeOutWarehouseBillEntity getBillEntity() {
        return billEntity;
    }

    public void setBillEntity(HandmadeOutWarehouseBillEntity billEntity) {
        this.billEntity = billEntity;
    }

    public OutWarehouseRemarkEntity getRemarkEntity() {
        return remarkEntity;
    }

    public void setRemarkEntity(OutWarehouseRemarkEntity remarkEntity) {
        this.remarkEntity = remarkEntity;
    }

    public List<OutWarehouseListEntity> getList() {
        return list;
    }

    public void setList(List<OutWarehouseListEntity> list) {
        this.list = list;
    }
}
