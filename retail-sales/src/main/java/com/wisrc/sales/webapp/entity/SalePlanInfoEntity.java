package com.wisrc.sales.webapp.entity;

import io.swagger.annotations.ApiModelProperty;

public class SalePlanInfoEntity {
    @ApiModelProperty(value = "销售计划ID", hidden = true)
    private String salePlanId;
    @ApiModelProperty(value = "类目主管ID")
    private String directorEmployeeId;
    @ApiModelProperty(value = "负责人ID", hidden = true)
    private String chargeEmployeeId;
    @ApiModelProperty(value = "类目主管姓名", hidden = true)
    private String directorEmployeeName;
    @ApiModelProperty(value = "负责人姓名", hidden = true)
    private String chargeEmployeeName;
    @ApiModelProperty(value = "商品状态", hidden = true)
    private String commodityStatus;
    @ApiModelProperty(value = "库存SKU", hidden = true)
    private String stockSku;
    @ApiModelProperty(value = "商品名称", hidden = true)
    private String commodityName;
    /*   @ApiModelProperty(value = "计划年月")
       private String planDate;*/
    @ApiModelProperty(value = "商品ID")
    private String commodityId;
    @ApiModelProperty(value = "店铺ID")
    private String shopId;
    @ApiModelProperty(value = "店铺名称", hidden = true)
    private String shopName;
    @ApiModelProperty(value = "MSKU")
    private String mskuId;
    /*  @ApiModelProperty(value = "重量")
      private double weight;*/
    @ApiModelProperty(value = "创建人", hidden = true)
    private String createUser;
    @ApiModelProperty(value = "创建时间", hidden = true)
    private String createTime;
    @ApiModelProperty(value = "更新人", hidden = true)
    private String modifyUser;
    @ApiModelProperty(value = "更新时间", hidden = true)
    private String modifyTime;
    @ApiModelProperty(value = "ASIN")
    private String asin;

    public String getAsin() {
        return asin;
    }

    public void setAsin(String asin) {
        this.asin = asin;
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

    public String getCommodityStatus() {
        return commodityStatus;
    }

    public void setCommodityStatus(String commodityStatus) {
        this.commodityStatus = commodityStatus;
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

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getSalePlanId() {
        return salePlanId;
    }

    public void setSalePlanId(String salePlanId) {
        this.salePlanId = salePlanId;
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

    public String getMskuId() {
        return mskuId;
    }

    public void setMskuId(String mskuId) {
        this.mskuId = mskuId;
    }


    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }
}
