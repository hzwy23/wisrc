package com.wisrc.shipment.webapp.vo;

public class ChangeLableViewVo {
    private String changeLabelId;
    private String sourceId;
    private String wareHouseId;
    private String fnsku;
    private String cancelReason;
    private String remark;
    private Integer statusCd;
    private String wareHouseName;
    private String operationStatusName;
    private Integer changeQuantity;
    private Integer changedQuantity;
    private String createTime;


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

    public String getOperationStatusName() {
        return operationStatusName;
    }

    public void setOperationStatusName(String operationStatusName) {
        this.operationStatusName = operationStatusName;
    }

    public Integer getChangeQuantity() {
        return changeQuantity;
    }

    public void setChangeQuantity(Integer changeQuantity) {
        this.changeQuantity = changeQuantity;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getChangedQuantity() {
        return changedQuantity;
    }

    public void setChangedQuantity(Integer changedQuantity) {
        this.changedQuantity = changedQuantity;
    }
}
