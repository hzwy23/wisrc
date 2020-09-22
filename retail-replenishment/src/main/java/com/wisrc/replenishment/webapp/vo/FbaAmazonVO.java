package com.wisrc.replenishment.webapp.vo;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;
import java.sql.Timestamp;

/**
 * 补充亚马逊信息VO
 */
public class FbaAmazonVO {

    @ApiModelProperty(value = "补货单ID")
    private String fbaReplenishmentId;
    @ApiModelProperty(value = "补货批次号")
    @NotEmpty(message = "请填写补货批次")
    private String batchNumber;
    @ApiModelProperty(value = "提货方式")
    private int pickupTypeCd;
    @ApiModelProperty(value = "refercenceId")
    private String refercenceId;
    @ApiModelProperty(value = "packlist文件")
    private String packlistFile;
    @ApiModelProperty(value = "package箱唛")
    private String packgeMark;
    @ApiModelProperty(value = "FnSKU条码")
    private String fnskuCode;
    @ApiModelProperty(hidden = true)
    private String modifyUser;
    @ApiModelProperty(hidden = true)
    private Timestamp modifyTime;

    @ApiModelProperty(value = "亚马逊收货仓地址")
    private String amazonWarehouseAddress;
    @ApiModelProperty(value = "亚马逊收货仓邮编")
    private String amazonWarehouseZipcode;
    @ApiModelProperty(value = "亚马逊收货仓编码")
    private String collectGoodsWarehouseId;


    public String getFbaReplenishmentId() {
        return fbaReplenishmentId;
    }

    public void setFbaReplenishmentId(String fbaReplenishmentId) {
        this.fbaReplenishmentId = fbaReplenishmentId;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public int getPickupTypeCd() {
        return pickupTypeCd;
    }

    public void setPickupTypeCd(int pickupTypeCd) {
        this.pickupTypeCd = pickupTypeCd;
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

    public String getPackgeMark() {
        return packgeMark;
    }

    public void setPackgeMark(String packgeMark) {
        this.packgeMark = packgeMark;
    }

    public String getFnskuCode() {
        return fnskuCode;
    }

    public void setFnskuCode(String fnskuCode) {
        this.fnskuCode = fnskuCode;
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

    public String getCollectGoodsWarehouseId() {
        return collectGoodsWarehouseId;
    }

    public void setCollectGoodsWarehouseId(String collectGoodsWarehouseId) {
        this.collectGoodsWarehouseId = collectGoodsWarehouseId;
    }
}
