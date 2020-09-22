package com.wisrc.replenishment.webapp.vo;

import io.swagger.annotations.ApiModelProperty;

public class CustomsListInfoVO {
    @ApiModelProperty(value = "产品信息唯一ID")
    private String uuid;
    @ApiModelProperty(value = "产品sku")
    private String mskuId;
    @ApiModelProperty(value = "装箱尺寸--长")
    private int outerBoxSpecificationLen;
    @ApiModelProperty(value = "装箱尺寸--宽")
    private int outerBoxSpecificationWidth;
    @ApiModelProperty(value = "装箱尺寸--高")
    private int outerBoxSpecificationHeight;
    @ApiModelProperty(value = "装量")
    private int packingQuantity;
    @ApiModelProperty(value = "总数量")
    private double totalAmount;
    @ApiModelProperty(value = "装箱箱数", hidden = true)
    private int numberOfBoxes;
    @ApiModelProperty(value = "装箱重量", hidden = true)
    private double packingWeight;
    @ApiModelProperty(value = "毛重", hidden = true)
    private double grossWeight;
    @ApiModelProperty(value = "净重", hidden = true)
    private double netWeight;
    @ApiModelProperty(value = "申报单价")
    private double declareUnitPrice;
    @ApiModelProperty(value = "单位")
    private int mskuUnitCd;
    @ApiModelProperty(value = "申报要素")
    private String declarationElements;
    @ApiModelProperty(value = "小计", hidden = true)
    private double declareSubtotal;

    private MskuInfoVO mskuInfoVO;

    public MskuInfoVO getMskuInfoVO() {
        return mskuInfoVO;
    }

    public void setMskuInfoVO(MskuInfoVO mskuInfoVO) {
        this.mskuInfoVO = mskuInfoVO;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getMskuId() {
        return mskuId;
    }

    public void setMskuId(String mskuId) {
        this.mskuId = mskuId;
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

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
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

    public double getGrossWeight() {
        return grossWeight;
    }

    public void setGrossWeight(double grossWeight) {
        this.grossWeight = grossWeight;
    }

    public double getNetWeight() {
        return netWeight;
    }

    public void setNetWeight(double netWeight) {
        this.netWeight = netWeight;
    }

    public double getDeclareUnitPrice() {
        return declareUnitPrice;
    }

    public void setDeclareUnitPrice(double declareUnitPrice) {
        this.declareUnitPrice = declareUnitPrice;
    }

    public int getMskuUnitCd() {
        return mskuUnitCd;
    }

    public void setMskuUnitCd(int mskuUnitCd) {
        this.mskuUnitCd = mskuUnitCd;
    }

    public String getDeclarationElements() {
        return declarationElements;
    }

    public void setDeclarationElements(String declarationElements) {
        this.declarationElements = declarationElements;
    }

    public double getDeclareSubtotal() {
        return declareSubtotal;
    }

    public void setDeclareSubtotal(double declareSubtotal) {
        this.declareSubtotal = declareSubtotal;
    }
}
