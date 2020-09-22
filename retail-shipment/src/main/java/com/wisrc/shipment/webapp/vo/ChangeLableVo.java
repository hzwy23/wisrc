package com.wisrc.shipment.webapp.vo;

import java.util.List;

public class ChangeLableVo {
    private String changeLabelId;
    private String sourceId;
    private String wareHouseId;
    private String fnsku;
    private String createTime;
    private String createUser;
    private String cancelReason;
    private Integer statusCd;
    private String wareHouseName;
    private String skuId;
    private String remark;
    private String operationStatusName;
    private String skuNameZh;
    private String picture;
    //fnsku条码文件id
    private String fnskuCodeId;
    private String subWarehouseId;

    private List<ChangeLabelDetailVo> changeLableDetailList;

    public String getChangeLabelId() {
        return changeLabelId;
    }

    public void setChangeLabelId(String changeLabelId) {
        this.changeLabelId = changeLabelId;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getWareHouseId() {
        return wareHouseId;
    }

    public void setWareHouseId(String wareHouseId) {
        this.wareHouseId = wareHouseId;
    }

    public String getFnsku() {
        return fnsku;
    }

    public void setFnsku(String fnsku) {
        this.fnsku = fnsku;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    public Integer getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(Integer statusCd) {
        this.statusCd = statusCd;
    }

    public String getWareHouseName() {
        return wareHouseName;
    }

    public void setWareHouseName(String wareHouseName) {
        this.wareHouseName = wareHouseName;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getOperationStatusName() {
        return operationStatusName;
    }

    public void setOperationStatusName(String operationStatusName) {
        this.operationStatusName = operationStatusName;
    }

    public String getFnskuCodeId() {
        return fnskuCodeId;
    }

    public void setFnskuCodeId(String fnskuCodeId) {
        this.fnskuCodeId = fnskuCodeId;
    }

    public List<ChangeLabelDetailVo> getChangeLableDetailList() {
        return changeLableDetailList;
    }

    public void setChangeLableDetailList(List<ChangeLabelDetailVo> changeLableDetailList) {
        this.changeLableDetailList = changeLableDetailList;
    }

    public String getSkuNameZh() {
        return skuNameZh;
    }

    public void setSkuNameZh(String skuNameZh) {
        this.skuNameZh = skuNameZh;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getSubWarehouseId() {
        return subWarehouseId;
    }

    public void setSubWarehouseId(String subWarehouseId) {
        this.subWarehouseId = subWarehouseId;
    }
}
