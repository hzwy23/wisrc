package com.wisrc.replenishment.webapp.entity;

import io.swagger.annotations.ApiModelProperty;

public class TransferBasicInfoEntity {
    @ApiModelProperty("调拨单号")
    private String transferOrderCd;
    @ApiModelProperty("运单号")
    private String waybillId;
    @ApiModelProperty("起运仓")
    private String warehouseStartId;
    @ApiModelProperty("目的仓")
    private String warehouseEndId;
    @ApiModelProperty("目的仓分仓")
    private String subWarehouseEndId;
    @ApiModelProperty("调拨单状态")
    private Integer statusCd;
    @ApiModelProperty("渠道报价id")
    private String offerId;
    @ApiModelProperty("物流商id")
    private String shipmentId;
    @ApiModelProperty("是否需要标签，0表示需要，1表示不需要")
    private String whLabel;
    @ApiModelProperty("备注信息")
    private String remark;
    @ApiModelProperty("创建人")
    private String createUser;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("修改人")
    private String modifyUser;
    @ApiModelProperty("修改时间")
    private String modifyTime;
    @ApiModelProperty("取消原因")
    private String cancelReason;
    @ApiModelProperty("发货时间")
    private String deliveryDate;
    @ApiModelProperty("签收时间")
    private String signInDate;
    @ApiModelProperty("调拨类型  1表示普通调拨，2表示组装调拨")
    private String transferTypeCd;
    @ApiModelProperty("随机值")
    private String randomValue;

    public String getWaybillId() {
        return waybillId;
    }

    public void setWaybillId(String waybillId) {
        this.waybillId = waybillId;
    }

    public String getSubWarehouseEndId() {
        return subWarehouseEndId;
    }

    public void setSubWarehouseEndId(String subWarehouseEndId) {
        this.subWarehouseEndId = subWarehouseEndId;
    }

    public String getRandomValue() {
        return randomValue;
    }

    public void setRandomValue(String randomValue) {
        this.randomValue = randomValue;
    }

    public String getTransferTypeCd() {
        return transferTypeCd;
    }

    public void setTransferTypeCd(String transferTypeCd) {
        this.transferTypeCd = transferTypeCd;
    }

    public String getTransferOrderCd() {
        return transferOrderCd;
    }

    public void setTransferOrderCd(String transferOrderCd) {
        this.transferOrderCd = transferOrderCd;
    }

    public String getWarehouseStartId() {
        return warehouseStartId;
    }

    public void setWarehouseStartId(String warehouseStartId) {
        this.warehouseStartId = warehouseStartId;
    }

    public String getWarehouseEndId() {
        return warehouseEndId;
    }

    public void setWarehouseEndId(String warehouseEndId) {
        this.warehouseEndId = warehouseEndId;
    }

    public Integer getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(Integer statusCd) {
        this.statusCd = statusCd;
    }

    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    public String getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(String shipmentId) {
        this.shipmentId = shipmentId;
    }

    public String getWhLabel() {
        return whLabel;
    }

    public void setWhLabel(String whLabel) {
        this.whLabel = whLabel;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getSignInDate() {
        return signInDate;
    }

    public void setSignInDate(String signInDate) {
        this.signInDate = signInDate;
    }
}
