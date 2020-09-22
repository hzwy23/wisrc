package com.wisrc.replenishment.webapp.vo.delivery;

import com.wisrc.replenishment.webapp.vo.FbaLabelVO;
import io.swagger.annotations.ApiModelProperty;

import java.sql.Timestamp;
import java.util.List;

public class DeliverySelectVO {

    @ApiModelProperty(value = "是否允许交运，如果为true则能交运，为false表示之前的物流单没有取消不允许交运")
    private Boolean allow;
    @ApiModelProperty(value = "判断是否选择了物流商渠道，如果为true，进入确认界面，如果为false进入选择物流商界面")
    private Boolean select = false;//是否交运过
    @ApiModelProperty(value = "补货单号")
    private String fbaReplenishmentId;
    @ApiModelProperty(value = "创建时间")
    private Timestamp createTime;
    @ApiModelProperty(value = "补货批次")
    private String batchNumber;
    @ApiModelProperty(value = "店铺名称")
    private String shopName;
    @ApiModelProperty(value = "标签名称集合")
    private List<FbaLabelVO> LabelList;
    @ApiModelProperty(value = "发货仓名称")
    private String warehouseName;//发货仓名称
    @ApiModelProperty(value = "收货仓省份代码")
    private String provinceCode;//收货仓省份代码
    @ApiModelProperty(value = "收货仓邮编")
    private String zipCode;//收货仓邮编
    @ApiModelProperty(value = "物流商id")
    private String shipmentId;
    @ApiModelProperty(value = "物流商名称")
    private String shipmentName;
    @ApiModelProperty(value = "渠道名称")
    private String channelName;
    @ApiModelProperty(value = "提货方式")
    private String pickupTypeName;
    @ApiModelProperty(value = "报价id")
    private String offerId;
    @ApiModelProperty(value = "目的地集合")
    private List<String> destinationList;

    public Boolean getAllow() {
        return allow;
    }

    public void setAllow(Boolean allow) {
        this.allow = allow;
    }

    public Boolean getSelect() {
        return select;
    }

    public void setSelect(Boolean select) {
        this.select = select;
    }

    public String getFbaReplenishmentId() {
        return fbaReplenishmentId;
    }

    public void setFbaReplenishmentId(String fbaReplenishmentId) {
        this.fbaReplenishmentId = fbaReplenishmentId;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public List<FbaLabelVO> getLabelList() {
        return LabelList;
    }

    public void setLabelList(List<FbaLabelVO> labelList) {
        LabelList = labelList;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(String shipmentId) {
        this.shipmentId = shipmentId;
    }

    public String getShipmentName() {
        return shipmentName;
    }

    public void setShipmentName(String shipmentName) {
        this.shipmentName = shipmentName;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    public List<String> getDestinationList() {
        return destinationList;
    }

    public void setDestinationList(List<String> destinationList) {
        this.destinationList = destinationList;
    }

    public String getPickupTypeName() {
        return pickupTypeName;
    }

    public void setPickupTypeName(String pickupTypeName) {
        this.pickupTypeName = pickupTypeName;
    }
}
