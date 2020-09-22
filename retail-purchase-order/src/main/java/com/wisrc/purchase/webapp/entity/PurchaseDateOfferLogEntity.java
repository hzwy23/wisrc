package com.wisrc.purchase.webapp.entity;

import com.wisrc.purchase.webapp.utils.Time;

import java.sql.Timestamp;

public class PurchaseDateOfferLogEntity {
    private String uuid;
    private String supplierOfferId;
    private String handleTime;
    private String handleUser;
    private String modifyColumn;
    private String oldValue;
    private String newValue;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getSupplierOfferId() {
        return supplierOfferId;
    }

    public void setSupplierOfferId(String supplierOfferId) {
        this.supplierOfferId = supplierOfferId;
    }

    public String getHandleTime() {
        return handleTime;
    }

    public void setHandleTime(Timestamp handleTime) {
        this.handleTime = Time.format(handleTime);
    }

    public String getHandleUser() {
        return handleUser;
    }

    public void setHandleUser(String handleUser) {
        this.handleUser = handleUser;
    }

    public String getModifyColumn() {
        return modifyColumn;
    }

    public void setModifyColumn(String modifyColumn) {
        this.modifyColumn = modifyColumn;
    }

    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    @Override
    public String toString() {
        return "PurchaseDateOfferLogEntity{" +
                "uuid='" + uuid + '\'' +
                ", supplierOfferId='" + supplierOfferId + '\'' +
                ", handleTime='" + handleTime + '\'' +
                ", handleUser='" + handleUser + '\'' +
                ", modifyColumn='" + modifyColumn + '\'' +
                ", oldValue='" + oldValue + '\'' +
                ", newValue='" + newValue + '\'' +
                '}';
    }
}
