package com.wisrc.replenishment.webapp.vo;

import io.swagger.annotations.ApiModelProperty;

public class FbaMskuPackUpdateVO {

    @ApiModelProperty(value = "补货单商品唯一id,更新时需要传此字段，查询展示时此字段没用")
    private String replenishmentCommodityId;
    @ApiModelProperty(value = "外箱规格-长(cm)")
    private int outerBoxSpecificationLen;
    @ApiModelProperty(value = "外箱规格-宽(cm)")
    private int outerBoxSpecificationWidth;
    @ApiModelProperty(value = "外箱规格-高(cm)")
    private int outerBoxSpecificationHeight;
    @ApiModelProperty(value = "装量(PCS/箱)")
    private int packingQuantity;
    @ApiModelProperty(value = "装箱箱数")
    private int numberOfBoxes;
    @ApiModelProperty(value = "重量(kg/箱)")
    private double packingWeight;
    @ApiModelProperty(value = "是否标准箱")
    private int isStandard;

    public int getIsStandard() {
        return isStandard;
    }

    public void setIsStandard(int isStandard) {
        this.isStandard = isStandard;
    }

    public String getReplenishmentCommodityId() {
        return replenishmentCommodityId;
    }

    public void setReplenishmentCommodityId(String replenishmentCommodityId) {
        this.replenishmentCommodityId = replenishmentCommodityId;
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

    public int getPackingQuantity() {
        return packingQuantity;
    }

    public void setPackingQuantity(int packingQuantity) {
        this.packingQuantity = packingQuantity;
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
}
