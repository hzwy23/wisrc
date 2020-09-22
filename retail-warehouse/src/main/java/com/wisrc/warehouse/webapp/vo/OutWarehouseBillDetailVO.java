package com.wisrc.warehouse.webapp.vo;

import com.wisrc.warehouse.webapp.entity.HandmadeOutWarehouseBillEntity;
import com.wisrc.warehouse.webapp.entity.OutWarehouseListEntity;
import com.wisrc.warehouse.webapp.entity.OutWarehouseRemarkEntity;

import java.util.List;

public class OutWarehouseBillDetailVO {
    private HandmadeOutWarehouseBillEntity billEntity;
    private List<OutWarehouseRemarkEntity> remarkList;
    private List<OutWarehouseListEntity> commodityList;

    public HandmadeOutWarehouseBillEntity getBillEntity() {
        return billEntity;
    }

    public void setBillEntity(HandmadeOutWarehouseBillEntity billEntity) {
        this.billEntity = billEntity;
    }

    public List<OutWarehouseRemarkEntity> getRemarkList() {
        return remarkList;
    }

    public void setRemarkList(List<OutWarehouseRemarkEntity> remarkList) {
        this.remarkList = remarkList;
    }

    public List<OutWarehouseListEntity> getCommodityList() {
        return commodityList;
    }

    public void setCommodityList(List<OutWarehouseListEntity> commodityList) {
        this.commodityList = commodityList;
    }
}
