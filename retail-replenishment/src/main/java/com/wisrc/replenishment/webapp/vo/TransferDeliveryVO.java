package com.wisrc.replenishment.webapp.vo;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;

public class TransferDeliveryVO {

    @ApiModelProperty(value = "交运单编号")
    private String transferOrderCd;

    @ApiModelProperty(value = "创建时间")
    private String createTime;

    @ApiModelProperty(value = "起运仓")
    private String warehouseStartId;

    @ApiModelProperty(value = "目的仓")
    private String warehouseEndId;

    @ApiModelProperty(value = "目的分仓")
    private String subWarehouseEndId;

    public String getTransferOrderCd() {
        return transferOrderCd;
    }

    public void setTransferOrderCd(String transferOrderCd) {
        this.transferOrderCd = transferOrderCd;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
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

    public String getSubWarehouseEndId() {
        return subWarehouseEndId;
    }

    public void setSubWarehouseEndId(String subWarehouseEndId) {
        this.subWarehouseEndId = subWarehouseEndId;
    }
}
