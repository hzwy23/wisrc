package com.wisrc.replenishment.webapp.vo;

import com.wisrc.replenishment.webapp.entity.ExceptionTypeAttrEntity;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class FbaQueryResultVO {

    @ApiModelProperty(value = "补货单Id", hidden = true)
    private String fbaReplenishmentId;
    @ApiModelProperty(value = "创建时间")
    private String createDate;
    @ApiModelProperty(value = "补货批次")
    private String batchNumber;
    @ApiModelProperty(value = "发货仓")
    private String warehouseName;
    @ApiModelProperty(value = "收货仓省份代码")
    private String provinceCode;
    @ApiModelProperty(value = "收货仓邮编")
    private String zipCode;
    @ApiModelProperty(value = "物流渠道名称")
    private String shipChannelName;
    @ApiModelProperty(value = "状态编码")
    private int statusCd;
    @ApiModelProperty(value = "状态名称")
    private String statusCdName;
    @ApiModelProperty(value = "物流单号")
    private String logisticsId;
    @ApiModelProperty(value = "物流交运状态")
    private int logisticsTypeCd;
    @ApiModelProperty(value = "物流异常说明")
    private List<ExceptionTypeAttrEntity> exceptionTypeDescList;
    @ApiModelProperty(value = "补货商品信息")
    private List<FbaMskuInfoVO> mskuList;
    @ApiModelProperty(value = "补货标签信息")
    private List<FbaLabelVO> labelList;
    @ApiModelProperty(value = "物流商id")
    private String shipmentId;
    @ApiModelProperty(value = "物流报价Id")
    private String offerId;
    @ApiModelProperty(value = "提货方式")


    private int pickupTypeCd;

    @ApiModelProperty(value = "亚马逊收货仓地址")
    private String amazonWarehouseAddress;
    @ApiModelProperty(value = "亚马逊收货仓邮编")
    private String amazonWarehouseZipcode;

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

    public int getLogisticsTypeCd() {
        return logisticsTypeCd;
    }

    public void setLogisticsTypeCd(int logisticsTypeCd) {
        this.logisticsTypeCd = logisticsTypeCd;
    }

    public String getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(String shipmentId) {
        this.shipmentId = shipmentId;
    }

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

    public String getShipChannelName() {
        return shipChannelName;
    }

    public void setShipChannelName(String shipChannelName) {
        this.shipChannelName = shipChannelName;
    }

    public int getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(int statusCd) {
        this.statusCd = statusCd;
    }

    public String getStatusCdName() {
        return statusCdName;
    }

    public void setStatusCdName(String statusCdName) {
        this.statusCdName = statusCdName;
    }

    public String getLogisticsId() {
        return logisticsId;
    }

    public void setLogisticsId(String logisticsId) {
        this.logisticsId = logisticsId;
    }

    public List<ExceptionTypeAttrEntity> getExceptionTypeDescList() {
        return exceptionTypeDescList;
    }

    public void setExceptionTypeDescList(List<ExceptionTypeAttrEntity> exceptionTypeDescList) {
        this.exceptionTypeDescList = exceptionTypeDescList;
    }

    public List<FbaMskuInfoVO> getMskuList() {
        return mskuList;
    }

    public void setMskuList(List<FbaMskuInfoVO> mskuList) {
        this.mskuList = mskuList;
    }

    public List<FbaLabelVO> getLabelList() {
        return labelList;
    }

    public void setLabelList(List<FbaLabelVO> labelList) {
        this.labelList = labelList;
    }

    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    public int getPickupTypeCd() {
        return pickupTypeCd;
    }

    public void setPickupTypeCd(int pickupTypeCd) {
        this.pickupTypeCd = pickupTypeCd;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
