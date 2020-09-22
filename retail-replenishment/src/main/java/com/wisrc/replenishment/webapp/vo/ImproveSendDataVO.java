package com.wisrc.replenishment.webapp.vo;

import io.swagger.annotations.ApiModelProperty;

public class ImproveSendDataVO {
    @ApiModelProperty(value = "产品信息唯一标识")
    private String uuid;
    @ApiModelProperty(value = "FBA补货ID")
    private String fbaReplenishmentId;
    @ApiModelProperty(value = "产品Id")
    private String mskuId;
    @ApiModelProperty(value = "库存SKU")
    private String storeSku;
    @ApiModelProperty(value = "产品名称")
    private String mskuName;
    @ApiModelProperty(value = "补货批次")
    private String replenishmentBatch;
    @ApiModelProperty(value = "装箱尺寸--长")
    private int outerBoxSpecificationLen;
    @ApiModelProperty(value = "装箱尺寸--宽")
    private int outerBoxSpecificationWidth;
    @ApiModelProperty(value = "装箱尺寸--高")
    private int outerBoxSpecificationHeight;
    @ApiModelProperty(value = "箱数")
    private int numberOfBoxes;
    @ApiModelProperty(value = "重量（KG/箱）")
    private double packingWeight;
    @ApiModelProperty(value = "总数量")
    private double totalAmount;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getFbaReplenishmentId() {
        return fbaReplenishmentId;
    }

    public void setFbaReplenishmentId(String fbaReplenishmentId) {
        this.fbaReplenishmentId = fbaReplenishmentId;
    }

    public String getMskuId() {
        return mskuId;
    }

    public void setMskuId(String mskuId) {
        this.mskuId = mskuId;
    }

    public String getStoreSku() {
        return storeSku;
    }

    public void setStoreSku(String storeSku) {
        this.storeSku = storeSku;
    }

    public String getMskuName() {
        return mskuName;
    }

    public void setMskuName(String mskuName) {
        this.mskuName = mskuName;
    }

    public String getReplenishmentBatch() {
        return replenishmentBatch;
    }

    public void setReplenishmentBatch(String replenishmentBatch) {
        this.replenishmentBatch = replenishmentBatch;
    }

    public int getOuterBoxSpecificationLen() {
        return outerBoxSpecificationLen;
    }

    public void setOuterBoxSpecificationLen(int outerBoxSpecificationLen) {
        this.outerBoxSpecificationLen = outerBoxSpecificationLen;
    }

    public int getOuterBoxSpecificationWidth() {
        return outerBoxSpecificationWidth;
    }

    public void setOuterBoxSpecificationWidth(int outerBoxSpecificationWidth) {
        this.outerBoxSpecificationWidth = outerBoxSpecificationWidth;
    }

    public int getOuterBoxSpecificationHeight() {
        return outerBoxSpecificationHeight;
    }

    public void setOuterBoxSpecificationHeight(int outerBoxSpecificationHeight) {
        this.outerBoxSpecificationHeight = outerBoxSpecificationHeight;
    }

    public int getNumberOfBoxes() {
        return numberOfBoxes;
    }

    public void setNumberOfBoxes(int numberOfBoxes) {
        this.numberOfBoxes = numberOfBoxes;
    }

    public double getPackingWeight() {
        return packingWeight;
    }

    public void setPackingWeight(double packingWeight) {
        this.packingWeight = packingWeight;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
}
