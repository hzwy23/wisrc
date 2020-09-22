package com.wisrc.warehouse.webapp.vo.syncVO;


import io.swagger.annotations.ApiModelProperty;

import java.sql.Timestamp;
import java.util.List;

public class FbaReplenishmentInfoVO {

    @ApiModelProperty(value = "补货单基本信息")
    private FbaReplenishmentInfoEntity infoEntity;
    @ApiModelProperty(value = "补货单商品详细信息")
    private List<FbaMskuInfoVO> mskuInfoList;
    @ApiModelProperty(value = "标签名称集合")
    private Timestamp createDate;//创建日期
    @ApiModelProperty(value = "发货仓名称")
    private String warehouseName;//发货仓名称
    @ApiModelProperty(value = "店铺名称")
    private String shopName;//店铺名称

    @ApiModelProperty(value = "收货仓省份代码")
    private String provinceCode;//收货仓省份代码
    @ApiModelProperty(value = "收货仓邮编")
    private String zipCode;//收货仓邮编
    @ApiModelProperty(value = "物流单号")
    private String shipmentNumber;//物流单号
    @ApiModelProperty(value = "物流异常说明")
    private String shipmentExceptionDesc;//物流异常说明
    @ApiModelProperty(value = "取消原因")
    private String cancelReason;


    public FbaReplenishmentInfoEntity getInfoEntity() {
        return infoEntity;
    }

    public void setInfoEntity(FbaReplenishmentInfoEntity infoEntity) {
        this.infoEntity = infoEntity;
    }

    public List<FbaMskuInfoVO> getMskuInfoList() {
        return mskuInfoList;
    }

    public void setMskuInfoList(List<FbaMskuInfoVO> mskuInfoList) {
        this.mskuInfoList = mskuInfoList;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
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

    public String getShipmentNumber() {
        return shipmentNumber;
    }

    public void setShipmentNumber(String shipmentNumber) {
        this.shipmentNumber = shipmentNumber;
    }

    public String getShipmentExceptionDesc() {
        return shipmentExceptionDesc;
    }

    public void setShipmentExceptionDesc(String shipmentExceptionDesc) {
        this.shipmentExceptionDesc = shipmentExceptionDesc;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }
}
