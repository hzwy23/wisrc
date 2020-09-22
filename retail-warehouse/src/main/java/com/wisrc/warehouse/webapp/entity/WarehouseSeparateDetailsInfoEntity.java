package com.wisrc.warehouse.webapp.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;

@ApiModel
public class WarehouseSeparateDetailsInfoEntity {

    @ApiModelProperty(value = "分仓编码，新增时不需要填写这个字段，系统默认自动生成")
    private String subWarehouseId;

    @ApiModelProperty(value = "主仓库编码")
    @NotEmpty(message = "仓库编码不能为空")
    private String warehouseId;

    @ApiModelProperty(value = "分仓名称")
    @NotEmpty(message = "分仓名称不能为空")
    private String separateWarehouseName;

    @ApiModelProperty(value = "删除标识，0：正常，1：已删除", hidden = true, required = false)
    private int deleteStatus;

    @ApiModelProperty(value = "创建人", required = false, hidden = true)
    private String createUser;
    @ApiModelProperty(value = "最近修改人", required = false, hidden = true)
    private String modifyUser;
    @ApiModelProperty(value = "创建时间", required = false, hidden = true)
    private String createTime;
    @ApiModelProperty(value = "最近修改时间", required = false, hidden = true)
    private String modifyTime;

    @ApiModelProperty(value = "随机数", required = false, hidden = true)
    private String randomValue;


    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getSubWarehouseId() {
        return subWarehouseId;
    }

    public void setSubWarehouseId(String subWarehouseId) {
        this.subWarehouseId = subWarehouseId;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getSeparateWarehouseName() {
        return separateWarehouseName;
    }

    public void setSeparateWarehouseName(String separateWarehouseName) {
        this.separateWarehouseName = separateWarehouseName;
    }

    public int getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(int deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    public String getRandomValue() {
        return randomValue;
    }

    public void setRandomValue(String randomValue) {
        this.randomValue = randomValue;
    }

    @Override
    public String toString() {
        return "WarehouseSeparateDetailsInfoEntity{" +
                "subWarehouseId='" + subWarehouseId + '\'' +
                ", warehouseId='" + warehouseId + '\'' +
                ", separateWarehouseName='" + separateWarehouseName + '\'' +
                ", delete_status='" + deleteStatus + '\'' +
                ", createUser='" + createUser + '\'' +
                ", modifyUser='" + modifyUser + '\'' +
                ", createTime='" + createTime + '\'' +
                ", modifyTime='" + modifyTime + '\'' +
                '}';
    }
}
