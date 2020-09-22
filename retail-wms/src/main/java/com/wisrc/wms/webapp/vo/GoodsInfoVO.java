package com.wisrc.wms.webapp.vo;

import com.fasterxml.jackson.annotation.JsonInclude;

public class GoodsInfoVO {
    private String goodsCode;
    private String goodsName;
    //本次质检合格总数
    private double qualifiedQuantity;
    //本次质检待检总数
    private double unQcQuantity;
    //本次质检总数
    private double totalQcQuantity;
    //本次质检不合格数
    private double unQualifiedQuantity;
    //物料个数
    private double unitQuantity;
    //物料箱数
    private double packageQuantity;
    //物料箱数
    private double totalQuantity;
    //生产批次
    private String productionBatchNo;
    private double packageCapacity;
    private double grossWeight;
    private double length;
    private double width;
    private double height;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String fnCode;
    //行号
    private int lineNum;

    //备品率
    private Double receiptRate;
    //产品型号
    private String productModel;
    //申报单位
    private String unit;

    public Double getReceiptRate() {
        return receiptRate;
    }

    public void setReceiptRate(Double receiptRate) {
        this.receiptRate = receiptRate;
    }

    public String getProductModel() {
        return productModel;
    }

    public void setProductModel(String productModel) {
        this.productModel = productModel;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getFnCode() {
        return fnCode;
    }

    public void setFnCode(String fnCode) {
        this.fnCode = fnCode;
    }

    public double getQualifiedQuantity() {
        return qualifiedQuantity;
    }

    public void setQualifiedQuantity(double qualifiedQuantity) {
        this.qualifiedQuantity = qualifiedQuantity;
    }

    public double getUnQcQuantity() {
        return unQcQuantity;
    }

    public void setUnQcQuantity(double unQcQuantity) {
        this.unQcQuantity = unQcQuantity;
    }

    public double getTotalQcQuantity() {
        return totalQcQuantity;
    }

    public void setTotalQcQuantity(double totalQcQuantity) {
        this.totalQcQuantity = totalQcQuantity;
    }

    public double getUnQualifiedQuantity() {
        return unQualifiedQuantity;
    }

    public void setUnQualifiedQuantity(double unQualifiedQuantity) {
        this.unQualifiedQuantity = unQualifiedQuantity;
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

    public double getUnitQuantity() {
        return unitQuantity;
    }

    public void setUnitQuantity(double unitQuantity) {
        this.unitQuantity = unitQuantity;
    }

    public double getPackageQuantity() {
        return packageQuantity;
    }

    public void setPackageQuantity(double packageQuantity) {
        this.packageQuantity = packageQuantity;
    }

    public double getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(double totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public String getProductionBatchNo() {
        return productionBatchNo;
    }

    public void setProductionBatchNo(String productionBatchNo) {
        this.productionBatchNo = productionBatchNo;
    }

    public double getPackageCapacity() {
        return packageCapacity;
    }

    public void setPackageCapacity(double packageCapacity) {
        this.packageCapacity = packageCapacity;
    }

    public double getGrossWeight() {
        return grossWeight;
    }

    public void setGrossWeight(double grossWeight) {
        this.grossWeight = grossWeight;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public int getLineNum() {
        return lineNum;
    }

    public void setLineNum(int lineNum) {
        this.lineNum = lineNum;
    }
}
