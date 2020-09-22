package com.wisrc.purchase.webapp.vo.returnVO;

import io.swagger.annotations.ApiModelProperty;

public class SkuInfoVO {
    @ApiModelProperty("库存sku")
    private String skuId;
    @ApiModelProperty("行号")
    private String lineNumber;
    @ApiModelProperty("fnSku")
    private String fnSkuId;
    @ApiModelProperty("参评名称")
    private String skuName;
    @ApiModelProperty("装箱长")
    private double outerBoxSpecificationLen;
    @ApiModelProperty("装箱宽")
    private double outerBoxSpecificationWidth;
    @ApiModelProperty("装箱高")
    private double outerBoxSpecificationHeight;
    @ApiModelProperty("箱数")
    private Integer numberOfBoxes;
    @ApiModelProperty("装量")
    private Integer packingQuantity;
    @ApiModelProperty("装箱重量")
    private Integer packingWeight;
    @ApiModelProperty("总重量")
    private double deliveryNumber;
    @ApiModelProperty("总数量")
    private Integer replenishmentQuantity;
    @ApiModelProperty("数量")
    private Integer quantity;
    @ApiModelProperty("收备品数")
    private Integer getSpareQuantity;

    public Integer getGetSpareQuantity() {
        return getSpareQuantity;
    }

    public void setGetSpareQuantity(Integer getSpareQuantity) {
        this.getSpareQuantity = getSpareQuantity;
    }

    public String getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(String lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getFnSkuId() {
        return fnSkuId;
    }

    public void setFnSkuId(String fnSkuId) {
        this.fnSkuId = fnSkuId;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public double getOuterBoxSpecificationLen() {
        return outerBoxSpecificationLen;
    }

    public void setOuterBoxSpecificationLen(double outerBoxSpecificationLen) {
        this.outerBoxSpecificationLen = outerBoxSpecificationLen;
    }

    public double getOuterBoxSpecificationWidth() {
        return outerBoxSpecificationWidth;
    }

    public void setOuterBoxSpecificationWidth(double outerBoxSpecificationWidth) {
        this.outerBoxSpecificationWidth = outerBoxSpecificationWidth;
    }

    public double getOuterBoxSpecificationHeight() {
        return outerBoxSpecificationHeight;
    }

    public void setOuterBoxSpecificationHeight(double outerBoxSpecificationHeight) {
        this.outerBoxSpecificationHeight = outerBoxSpecificationHeight;
    }

    public Integer getNumberOfBoxes() {
        return numberOfBoxes;
    }

    public void setNumberOfBoxes(Integer numberOfBoxes) {
        this.numberOfBoxes = numberOfBoxes;
    }

    public Integer getPackingQuantity() {
        return packingQuantity;
    }

    public void setPackingQuantity(Integer packingQuantity) {
        this.packingQuantity = packingQuantity;
    }

    public Integer getPackingWeight() {
        return packingWeight;
    }

    public void setPackingWeight(Integer packingWeight) {
        this.packingWeight = packingWeight;
    }

    public double getDeliveryNumber() {
        return deliveryNumber;
    }

    public void setDeliveryNumber(double deliveryNumber) {
        this.deliveryNumber = deliveryNumber;
    }

    public Integer getReplenishmentQuantity() {
        return replenishmentQuantity;
    }

    public void setReplenishmentQuantity(Integer replenishmentQuantity) {
        this.replenishmentQuantity = replenishmentQuantity;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
