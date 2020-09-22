package com.wisrc.shipment.webapp.vo;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class LabelDetailDealVo {
    @NotNull
    private Integer operationStatusCd;
    @NotNull
    private Integer changedQuantity;
    @ApiModelProperty(value = "完成时间", hidden = true)
    private String completeTime;
    private String cancelReason;
    @ApiModelProperty(value = "更新时间", hidden = true)
    private String modifyUser;
    @NotEmpty(message = "换标Id不能为空")
    private String changeLabelId;
    @NotEmpty(message = "uuid不能为空")
    private String uuid;

    public Integer getOperationStatusCd() {
        return operationStatusCd;
    }

    public void setOperationStatusCd(Integer operationStatusCd) {
        this.operationStatusCd = operationStatusCd;
    }

    public Integer getChangedQuantity() {
        return changedQuantity;
    }

    public void setChangedQuantity(Integer changedQuantity) {
        this.changedQuantity = changedQuantity;
    }

    public String getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(String completeTime) {
        this.completeTime = completeTime;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getChangeLabelId() {
        return changeLabelId;
    }

    public void setChangeLabelId(String changeLabelId) {
        this.changeLabelId = changeLabelId;
    }
}
