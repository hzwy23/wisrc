package com.wisrc.quality.webapp.AOP.wmsvo;

import io.swagger.annotations.ApiModelProperty;

public class InspectionItemCheckDetailsVO {

    @ApiModelProperty(value = "行号")
    private String lineNum;

    @ApiModelProperty(value = "库存SKU")
    private String goodsCode;

    @ApiModelProperty(value = "产品中文名")
    private String goodsName;

    @ApiModelProperty(value = "提货数量")
    private Integer unQcQuantity;

    @ApiModelProperty(value = "合格数")
    private Integer qualifiedQuantity;

    @ApiModelProperty(value = "不合格数")
    private Integer unQualifiedQuantity;

    @ApiModelProperty(value = "本次验货数量")
    private Integer totalQuantity;

    public String getLineNum() {
        return lineNum;
    }

    public void setLineNum(String lineNum) {
        this.lineNum = lineNum;
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Integer getUnQcQuantity() {
        return unQcQuantity;
    }

    public void setUnQcQuantity(Integer unQcQuantity) {
        this.unQcQuantity = unQcQuantity;
    }

    public Integer getQualifiedQuantity() {
        return qualifiedQuantity;
    }

    public void setQualifiedQuantity(Integer qualifiedQuantity) {
        this.qualifiedQuantity = qualifiedQuantity;
    }

    public Integer getUnQualifiedQuantity() {
        return unQualifiedQuantity;
    }

    public void setUnQualifiedQuantity(Integer unQualifiedQuantity) {
        this.unQualifiedQuantity = unQualifiedQuantity;
    }

    public Integer getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(Integer totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    @Override
    public String toString() {
        return "InspectionItemCheckDetailsVO{" +
                "lineNum='" + lineNum + '\'' +
                ", goodsCode='" + goodsCode + '\'' +
                ", goodsName='" + goodsName + '\'' +
                ", unQcQuantity=" + unQcQuantity +
                ", qualifiedQuantity=" + qualifiedQuantity +
                ", unQualifiedQuantity=" + unQualifiedQuantity +
                ", totalQuantity=" + totalQuantity +
                '}';
    }
}
