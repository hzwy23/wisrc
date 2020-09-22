package com.wisrc.replenishment.webapp.entity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

@Api(tags = "跟踪单中补货单相关信息")
public class WaybillReplenishmentEntity {
    @ApiModelProperty(value = "补货单")
    private String fbaReplenishmentId;
    @ApiModelProperty(value = "补货批次")
    private String batchNumber;
    @ApiModelProperty(value = "发货数量")
    private int deliveringQuantity;
    @ApiModelProperty(value = " 发货仓ID")
    private String warehouseId;
    @ApiModelProperty(value = "提货方式编码")
    private int pickupTypeCd;
    @ApiModelProperty(value = "提货方式编码")
    private String pickupTypeName;

    public String getPickupTypeName() {
        return pickupTypeName;
    }

    public void setPickupTypeName(String pickupTypeName) {
        this.pickupTypeName = pickupTypeName;
    }

    public int getPickupTypeCd() {
        return pickupTypeCd;
    }

    public void setPickupTypeCd(int pickupTypeCd) {
        this.pickupTypeCd = pickupTypeCd;
    }

    public String getFbaReplenishmentId() {
        return fbaReplenishmentId;
    }

    public void setFbaReplenishmentId(String fbaReplenishmentId) {
        this.fbaReplenishmentId = fbaReplenishmentId;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public int getDeliveringQuantity() {
        return deliveringQuantity;
    }

    public void setDeliveringQuantity(int deliveringQuantity) {
        this.deliveringQuantity = deliveringQuantity;
    }

    @Override
    public String toString() {
        return "WaybillReplenishmentEntity{" +
                "fbaReplenishmentId='" + fbaReplenishmentId + '\'' +
                ", batchNumber='" + batchNumber + '\'' +
                ", deliveringQuantity=" + deliveringQuantity +
                ", warehouseId='" + warehouseId + '\'' +
                '}';
    }
}
