package com.wisrc.shipment.webapp.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class ChangeLableDetail {
    @NotNull(message = "申请换标数不能为空")
    private Integer changeQuantity;
    @NotEmpty(message = "来源fnsku不能为空")
    private String fnsku;
    @ApiModelProperty(value = "取消原因", hidden = true)
    private String cancelReason;
    private String warehousePosition;
    @ApiModelProperty(value = "状态", hidden = true)
    private Integer operationStatusCd;
    @ApiModelProperty(value = "更新时间", hidden = true)
    private String modifyTime;
    @ApiModelProperty(value = "更新人", hidden = true)
    private String modifyUser;
    @ApiModelProperty(value = "换标ID", hidden = true)
    private String changeLabelId;
    @ApiModelProperty(value = "uuid", hidden = true)
    private String uuid;
    @ApiModelProperty(value = "实际换标数", hidden = true)
    private Integer changedQuantity;
    @NotEmpty(message = "分仓不能为空")
    private String subWarehouseId;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }


    public String getFnsku() {
        return fnsku;
    }

    public void setFnsku(String fnsku) {
        this.fnsku = fnsku;
    }

    public String getWarehousePosition() {
        return warehousePosition;
    }

    public void setWarehousePosition(String warehousePosition) {
        this.warehousePosition = warehousePosition;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    public Integer getOperationStatusCd() {
        return operationStatusCd;
    }

    public void setOperationStatusCd(Integer operationStatusCd) {
        this.operationStatusCd = operationStatusCd;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    public String getChangeLabelId() {
        return changeLabelId;
    }

    public void setChangeLabelId(String changeLabelId) {
        this.changeLabelId = changeLabelId;
    }

    public Integer getChangeQuantity() {
        return changeQuantity;
    }

    public void setChangeQuantity(Integer changeQuantity) {
        this.changeQuantity = changeQuantity;
    }

    public Integer getChangedQuantity() {
        return changedQuantity;
    }

    public void setChangedQuantity(Integer changedQuantity) {
        this.changedQuantity = changedQuantity;
    }

    public String getSubWarehouseId() {
        return subWarehouseId;
    }

    public void setSubWarehouseId(String subWarehouseId) {
        this.subWarehouseId = subWarehouseId;
    }
}
