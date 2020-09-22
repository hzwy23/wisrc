package com.wisrc.sales.webapp.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SaleEstimateVo {
    private String asinId;
    private String mskuId;
    private String shopId;
    private String shopName;
    private String productName;
    private int mskuStatusId;
    private String mskuStatus;
    private String modifyUser;
    private String modifyTime;
    private String modifyUserName;
    private String skuId;
    private String estimateId;
    private String commodityId;
    private String createUser;
    private String chargePerson;//负责人名称
    private String chargeEmployeeId; //负责人id
    private String directorEmployeeId;//类目主管id
    private String directorEmployeeName;//类目主管名称

    // 主管审批状态
    private String directApprovStatus;
    // 经理审批状态
    private String managerApprovStatus;
    // 计划部审批状态
    private String planDepartApprovStatus;
    // 主管备注
    private String directApprovRemark;
    // 经理备注
    private String managerApprovRemark;
    // 计划部备注
    private String planDepartApprovRemark;
    @ApiModelProperty(value = "店铺卖家编号")
    private String shopSellerId;

    @ApiModelProperty(value = "更新标识", required = false, hidden = true)
    private Integer updateFlag;

    public String getAsinId() {
        return asinId;
    }

    public void setAsinId(String asinId) {
        this.asinId = asinId;
    }

    public String getMskuId() {
        return mskuId;
    }

    public void setMskuId(String mskuId) {
        this.mskuId = mskuId;
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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getMskuStatusId() {
        return mskuStatusId;
    }

    public void setMskuStatusId(int mskuStatusId) {
        this.mskuStatusId = mskuStatusId;
    }

    public String getMskuStatus() {
        return mskuStatus;
    }

    public void setMskuStatus(String mskuStatus) {
        this.mskuStatus = mskuStatus;
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

    public String getModifyUserName() {
        return modifyUserName;
    }

    public void setModifyUserName(String modifyUserName) {
        this.modifyUserName = modifyUserName;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getEstimateId() {
        return estimateId;
    }

    public void setEstimateId(String estimateId) {
        this.estimateId = estimateId;
    }

    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getChargePerson() {
        return chargePerson;
    }

    public void setChargePerson(String chargePerson) {
        this.chargePerson = chargePerson;
    }

    public String getChargeEmployeeId() {
        return chargeEmployeeId;
    }

    public void setChargeEmployeeId(String chargeEmployeeId) {
        this.chargeEmployeeId = chargeEmployeeId;
    }

    public String getDirectorEmployeeId() {
        return directorEmployeeId;
    }

    public void setDirectorEmployeeId(String directorEmployeeId) {
        this.directorEmployeeId = directorEmployeeId;
    }

    public String getDirectorEmployeeName() {
        return directorEmployeeName;
    }

    public void setDirectorEmployeeName(String directorEmployeeName) {
        this.directorEmployeeName = directorEmployeeName;
    }
}
