package com.wisrc.replenishment.webapp.entity;

import io.swagger.annotations.ApiModelProperty;

import java.sql.Timestamp;

/**
 * 补货单商品信息表
 */
public class FbaMskuInfoEntity {

    @ApiModelProperty(hidden = true)
    private String replenishmentCommodityId;
    @ApiModelProperty(hidden = true)
    private String fbaReplenishmentId;
    @ApiModelProperty(value = "商品Id", hidden = true)
    private String commodityId;
    @ApiModelProperty(value = "店铺Id", hidden = true)
    private String shopId;
    @ApiModelProperty(hidden = true)
    private String mskuId;
    @ApiModelProperty(value = "发货数量", hidden = true)
    private int deliveryNumber;
    @ApiModelProperty(value = "签收数量", position = 3)
    private int signQuantity;
    @ApiModelProperty(value = "补货量")
    private int replenishmentQuantity;
    //    @ApiModelProperty(value = "外箱规格-长(cm)")
//    private int outerBoxLen;
//    @ApiModelProperty(value = "外箱规格-宽(cm)")
//    private int outerBoxWidth;
//    @ApiModelProperty(value = "外箱规格-高(cm)")
//    private int outerBoxHeight;
//    @ApiModelProperty(value = "装量(PCS/箱)")
//    private int packingQuantity;
//    @ApiModelProperty(value = "装箱箱数")
//    private int numberOfBoxes;
//    @ApiModelProperty(value = "重量(kg/箱)")
//    private int packingWeight;
//    @ApiModelProperty(value = "发货数量")
//    private int deliveryNumber;
    @ApiModelProperty(value = "生成时间", hidden = true)
    private Timestamp createTime;
    @ApiModelProperty(value = "创建人", hidden = true)
    private String createUser;
    @ApiModelProperty(value = "最近修改人", hidden = true)
    private String modifyUser;
    @ApiModelProperty(value = "最近修改时间", hidden = true)
    private Timestamp modifyTime;
    @ApiModelProperty(value = "删除标识", hidden = true)
    private int deleteStatus;
    //    @ApiModelProperty(value = "签收数量")
//    private int signInQuantity;
    @ApiModelProperty(value = "清关名称")
    private String clearanceName;

    public int getReplenishmentQuantity() {
        return replenishmentQuantity;
    }

    public void setReplenishmentQuantity(int replenishmentQuantity) {
        this.replenishmentQuantity = replenishmentQuantity;
    }

    public int getDeliveryNumber() {
        return deliveryNumber;
    }

    public void setDeliveryNumber(int deliveryNumber) {
        this.deliveryNumber = deliveryNumber;
    }

    public int getSignQuantity() {
        return signQuantity;
    }

    public void setSignQuantity(int signQuantity) {
        this.signQuantity = signQuantity;
    }

    public String getReplenishmentCommodityId() {
        return replenishmentCommodityId;
    }

    public void setReplenishmentCommodityId(String replenishmentCommodityId) {
        this.replenishmentCommodityId = replenishmentCommodityId;
    }

    public String getFbaReplenishmentId() {
        return fbaReplenishmentId;
    }

    public void setFbaReplenishmentId(String fbaReplenishmentId) {
        this.fbaReplenishmentId = fbaReplenishmentId;
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

    public int getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(int deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    public Timestamp getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Timestamp modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getClearanceName() {
        return clearanceName;
    }

    public void setClearanceName(String clearanceName) {
        this.clearanceName = clearanceName;
    }

}
