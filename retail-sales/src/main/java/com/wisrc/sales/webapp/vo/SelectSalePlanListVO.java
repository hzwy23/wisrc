package com.wisrc.sales.webapp.vo;

import io.swagger.annotations.ApiModelProperty;

public class SelectSalePlanListVO {
    @ApiModelProperty(value = "销售计划ID")
    private String salePlanId;
    @ApiModelProperty(value = "ASIN")
    private String asin;
    @ApiModelProperty(value = "商品ID")
    private String commodityId;
    @ApiModelProperty(value = "店铺ID")
    private String shopId;
    @ApiModelProperty(value = "店铺ID")
    private String shopName;
    @ApiModelProperty(value = "MSKU")
    private String mskuId;
    @ApiModelProperty(value = "库存SKU")
    private String stockSku;
    @ApiModelProperty(value = "商品名称")
    private String commodityName;
    @ApiModelProperty(value = "商品状态")
    private String commodityStatus;
    @ApiModelProperty(value = "类目主管ID")
    private String directorEmployeeId;
    @ApiModelProperty(value = "负责人ID")
    private String chargeEmployeeId;
    @ApiModelProperty(value = "类目主管姓名")
    private String directorEmployeeName;
    @ApiModelProperty(value = "负责人姓名")
    private String chargeEmployeeName;
    @ApiModelProperty(value = "更新人ID")
    private String modifyUser;
    @ApiModelProperty(value = "更新人姓名")
    private String modifyUserName;
    @ApiModelProperty(value = "更新时间")
    private String modifyTime;
    @ApiModelProperty(value = "销售状态")
    private String salesStatusCd;


    public String getSalePlanId() {
        return salePlanId;
    }

    public void setSalePlanId(String salePlanId) {
        this.salePlanId = salePlanId;
    }

    public String getAsin() {
        return asin;
    }

    public void setAsin(String asin) {
        this.asin = asin;
    }

    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getMskuId() {
        return mskuId;
    }

    public void setMskuId(String mskuId) {
        this.mskuId = mskuId;
    }

    public String getStockSku() {
        return stockSku;
    }

    public void setStockSku(String stockSku) {
        this.stockSku = stockSku;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public String getCommodityStatus() {
        return commodityStatus;
    }

    public void setCommodityStatus(String commodityStatus) {
        this.commodityStatus = commodityStatus;
    }

    public String getDirectorEmployeeId() {
        return directorEmployeeId;
    }

    public void setDirectorEmployeeId(String directorEmployeeId) {
        this.directorEmployeeId = directorEmployeeId;
    }

    public String getChargeEmployeeId() {
        return chargeEmployeeId;
    }

    public void setChargeEmployeeId(String chargeEmployeeId) {
        this.chargeEmployeeId = chargeEmployeeId;
    }

    public String getDirectorEmployeeName() {
        return directorEmployeeName;
    }

    public void setDirectorEmployeeName(String directorEmployeeName) {
        this.directorEmployeeName = directorEmployeeName;
    }

    public String getChargeEmployeeName() {
        return chargeEmployeeName;
    }

    public void setChargeEmployeeName(String chargeEmployeeName) {
        this.chargeEmployeeName = chargeEmployeeName;
    }

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    public String getModifyUserName() {
        return modifyUserName;
    }

    public void setModifyUserName(String modifyUserName) {
        this.modifyUserName = modifyUserName;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getSalesStatusCd() {
        return salesStatusCd;
    }

    public void setSalesStatusCd(String salesStatusCd) {
        this.salesStatusCd = salesStatusCd;
    }

    @Override
    public String toString() {
        return "SelectSalePlanListVO{" +
                "salePlanId='" + salePlanId + '\'' +
                ", asin='" + asin + '\'' +
                ", commodityId='" + commodityId + '\'' +
                ", shopId='" + shopId + '\'' +
                ", shopName='" + shopName + '\'' +
                ", mskuId='" + mskuId + '\'' +
                ", stockSku='" + stockSku + '\'' +
                ", commodityName='" + commodityName + '\'' +
                ", commodityStatus='" + commodityStatus + '\'' +
                ", directorEmployeeId='" + directorEmployeeId + '\'' +
                ", chargeEmployeeId='" + chargeEmployeeId + '\'' +
                ", directorEmployeeName='" + directorEmployeeName + '\'' +
                ", chargeEmployeeName='" + chargeEmployeeName + '\'' +
                ", modifyUser='" + modifyUser + '\'' +
                ", modifyUserName='" + modifyUserName + '\'' +
                ", modifyTime='" + modifyTime + '\'' +
                ", salesStatusCd='" + salesStatusCd + '\'' +
                '}';
    }
}
