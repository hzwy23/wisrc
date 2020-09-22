package com.wisrc.warehouse.webapp.vo;

import com.wisrc.warehouse.webapp.entity.EnterWarehouseListEntity;
import com.wisrc.warehouse.webapp.entity.EnterWarehouseRemarkEntity;
import com.wisrc.warehouse.webapp.entity.HandmadeEnterWarehouseBillEntity;

import java.util.List;

public class EnterWarehouseBillDetailVO {
    private HandmadeEnterWarehouseBillEntity billEntity;
    private List<EnterWarehouseRemarkEntity> remarkList;
    private List<EnterWarehouseListEntity> commodityList;

    public HandmadeEnterWarehouseBillEntity getBillEntity() {
        return billEntity;
    }

    public void setBillEntity(HandmadeEnterWarehouseBillEntity billEntity) {
        this.billEntity = billEntity;
    }

    public List<EnterWarehouseRemarkEntity> getRemarkList() {
        return remarkList;
    }

    public void setRemarkList(List<EnterWarehouseRemarkEntity> remarkList) {
        this.remarkList = remarkList;
    }

    public List<EnterWarehouseListEntity> getCommodityList() {
        return commodityList;
    }

    public void setCommodityList(List<EnterWarehouseListEntity> commodityList) {
        this.commodityList = commodityList;
    }
}
