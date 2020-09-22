package com.wisrc.shipment.webapp.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

public class ChangeLableEnity {
    @ApiModelProperty(value = "换标单号", hidden = true)
    private String changeLabelId;
    private String sourceId;
    @NotEmpty(message = "换标仓库不能为空")
    private String warehouseId;
    @NotEmpty(message = "分仓不能为空")
    private String subWarehouseId;
    @NotEmpty(message = "目标fnsku不能为空")
    private String fnsku;
    @ApiModelProperty(value = "创建时间", hidden = true)
    private String createTime;
    @ApiModelProperty(value = "创建人", hidden = true)
    private String createUser;
    @ApiModelProperty(value = "更新时间", hidden = true)
    private String modifyTime;
    @ApiModelProperty(value = "更新人", hidden = true)
    private String modifyUser;
    @ApiModelProperty(value = "取消原因", hidden = true)
    private String cancelReason;
    @ApiModelProperty(value = "状态", hidden = true)
    private Integer statusCd;
    private String remark;
    @Valid
    @NotEmpty
    private List<ChangeLableDetail> changeLableDetailList;

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getFnsku() {
        return fnsku;
    }

    public void setFnsku(String fnsku) {
        this.fnsku = fnsku;
    }

    public List<ChangeLableDetail> getChangeLableDetailList() {
        return changeLableDetailList;
    }

    public void setChangeLableDetailList(List<ChangeLableDetail> changeLableDetailList) {
        this.changeLableDetailList = changeLableDetailList;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSubWarehouseId() {
        return subWarehouseId;
    }

    public void setSubWarehouseId(String subWarehouseId) {
        this.subWarehouseId = subWarehouseId;
    }
}
