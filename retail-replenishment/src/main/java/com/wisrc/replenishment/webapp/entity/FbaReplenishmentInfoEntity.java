package com.wisrc.replenishment.webapp.entity;

import io.swagger.annotations.ApiModelProperty;

import java.sql.Timestamp;

/**
 * 补货单信息表
 */
public class FbaReplenishmentInfoEntity {

    @ApiModelProperty(value = "补货单id")
    private String fbaReplenishmentId;
    @ApiModelProperty(value = "状态编码")
    private int statusCd;
    @ApiModelProperty(value = "店铺Id")
    private String shopId;
    //    @ApiModelProperty(value = "装箱数量")
//    private int packingQuantity;
//    @ApiModelProperty(value = "发货量")
//    private int deliveringQuantity;
    @ApiModelProperty(value = "起运仓")
    private String warehouseId;
    @ApiModelProperty("起运仓分仓id")
    private String subWarehouseId;
    @ApiModelProperty(value = "报价id")
    private String offerId;
    @ApiModelProperty(value = "渠道名称")
    private String channelName;
    @ApiModelProperty(value = "备注信息")
    private String remark;
    @ApiModelProperty(value = "下单时间", hidden = true)
    private Timestamp createTime;
    @ApiModelProperty(hidden = true)
    private String createUser;
    @ApiModelProperty(hidden = true)
    private String modifyUser;
    @ApiModelProperty(hidden = true)
    private Timestamp modifyTime;
    @ApiModelProperty(hidden = true)
    private int deleteStatus;
    @ApiModelProperty(value = "补货种类")
    private int replenishmentSpecies;
    @ApiModelProperty(value = "补货数量")
    private int replenishmentCount;
    @ApiModelProperty(value = "多个标签时，用'，'隔开")
    private String labelName;
    @ApiModelProperty(value = "补货批次")
    private String batchNumber;
    @ApiModelProperty(value = "refercenceid")
    private String refercenceId;
    @ApiModelProperty(value = "packlist文件")
    private String packlistFile;
    @ApiModelProperty(value = "pachage箱唛")
    private String packageMark;
    @ApiModelProperty(value = "fnsku")
    private String fnskuCode;
    @ApiModelProperty(value = "物流商id")
    private String shipmentId;
    @ApiModelProperty(value = "取消原因")
    private String cancelReason;
    @ApiModelProperty(value = "发货类型")
    private int deliveringTypeCd;
    @ApiModelProperty(value = "提货方式")
    private int pickupTypeCd;
    @ApiModelProperty(value = "单位")
    private int unitCd;
    @ApiModelProperty(value = "亚马逊收货仓地址")
    private String amazonWarehouseAddress;
    @ApiModelProperty(value = "亚马逊收货仓邮编")
    private String amazonWarehouseZipcode;
    @ApiModelProperty(value = "亚马逊收货仓编码", hidden = true)
    private String collectGoodsWarehouseId;

    @ApiModelProperty(value = "随机数", hidden = true)
    private String randomValue;

    public String getSubWarehouseId() {
        return subWarehouseId;
    }

    public void setSubWarehouseId(String subWarehouseId) {
        this.subWarehouseId = subWarehouseId;
    }

    public String getRandomValue() {
        return randomValue;
    }

    public void setRandomValue(String randomValue) {
        this.randomValue = randomValue;
    }

    public String getAmazonWarehouseAddress() {
        return amazonWarehouseAddress;
    }

    public void setAmazonWarehouseAddress(String amazonWarehouseAddress) {
        this.amazonWarehouseAddress = amazonWarehouseAddress;
    }

    public String getAmazonWarehouseZipcode() {
        return amazonWarehouseZipcode;
    }

    public void setAmazonWarehouseZipcode(String amazonWarehouseZipcode) {
        this.amazonWarehouseZipcode = amazonWarehouseZipcode;
    }

    public String getFbaReplenishmentId() {
        return fbaReplenishmentId;
    }

    public void setFbaReplenishmentId(String fbaReplenishmentId) {
        this.fbaReplenishmentId = fbaReplenishmentId;
    }

    public int getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(int statusCd) {
        this.statusCd = statusCd;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }


    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
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

    public int getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(int deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    public int getReplenishmentSpecies() {
        return replenishmentSpecies;
    }

    public void setReplenishmentSpecies(int replenishmentSpecies) {
        this.replenishmentSpecies = replenishmentSpecies;
    }

    public int getReplenishmentCount() {
        return replenishmentCount;
    }

    public void setReplenishmentCount(int replenishmentCount) {
        this.replenishmentCount = replenishmentCount;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public String getRefercenceId() {
        return refercenceId;
    }

    public void setRefercenceId(String refercenceId) {
        this.refercenceId = refercenceId;
    }

    public String getPacklistFile() {
        return packlistFile;
    }

    public void setPacklistFile(String packlistFile) {
        this.packlistFile = packlistFile;
    }

    public String getPackageMark() {
        return packageMark;
    }

    public void setPackageMark(String packageMark) {
        this.packageMark = packageMark;
    }

    public String getFnskuCode() {
        return fnskuCode;
    }

    public void setFnskuCode(String fnskuCode) {
        this.fnskuCode = fnskuCode;
    }

    public String getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(String shipmentId) {
        this.shipmentId = shipmentId;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public int getDeliveringTypeCd() {
        return deliveringTypeCd;
    }

    public void setDeliveringTypeCd(int deliveringTypeCd) {
        this.deliveringTypeCd = deliveringTypeCd;
    }

    public int getPickupTypeCd() {
        return pickupTypeCd;
    }

    public void setPickupTypeCd(int pickupTypeCd) {
        this.pickupTypeCd = pickupTypeCd;
    }

    public int getUnitCd() {
        return unitCd;
    }

    public void setUnitCd(int unitCd) {
        this.unitCd = unitCd;
    }

    public String getCollectGoodsWarehouseId() {
        return collectGoodsWarehouseId;
    }

    public void setCollectGoodsWarehouseId(String collectGoodsWarehouseId) {
        this.collectGoodsWarehouseId = collectGoodsWarehouseId;
    }
}
