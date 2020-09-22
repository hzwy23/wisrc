package com.wisrc.warehouse.webapp.entity;

import io.swagger.annotations.ApiModelProperty;

public class OutWarehouseListEntity {
    @ApiModelProperty(value = "唯一ID", hidden = true)
    private String uuid;
    @ApiModelProperty(value = "出库单编号", hidden = true)
    private String outBillId;
    @ApiModelProperty(value = "库存Sku")
    private String skuId;
    @ApiModelProperty(value = " 产品名称")
    private String skuName;

    @ApiModelProperty(value = "FnSKU")
    private String fnSkuId;

    @ApiModelProperty(value = "库位")
    private String warehousePosition;
    @ApiModelProperty(value = "出库库存")
    private int outWarehouseNum;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getOutBillId() {
        return outBillId;
    }

    public void setOutBillId(String outBillId) {
        this.outBillId = outBillId;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getWarehousePosition() {
        return warehousePosition;
    }

    public void setWarehousePosition(String warehousePosition) {
        this.warehousePosition = warehousePosition;
    }

    public int getOutWarehouseNum() {
        return outWarehouseNum;
    }

    public void setOutWarehouseNum(int outWarehouseNum) {
        this.outWarehouseNum = outWarehouseNum;
    }

    public String getFnSkuId() {
        return fnSkuId;
    }

    public void setFnSkuId(String fnSkuId) {
        this.fnSkuId = fnSkuId;
    }

    @Override
    public String toString() {
        return "OutWarehouseListEntity{" +
                "uuid='" + uuid + '\'' +
                ", outBillId='" + outBillId + '\'' +
                ", skuId='" + skuId + '\'' +
                ", skuName='" + skuName + '\'' +
                ", fnSkuId='" + fnSkuId + '\'' +
                ", warehousePosition='" + warehousePosition + '\'' +
                ", outWarehouseNum=" + outWarehouseNum +
                '}';
    }
}
