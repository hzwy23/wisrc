package com.wisrc.warehouse.webapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class WarehouseManageInfoEntity {
    @ApiModelProperty(value = "仓库唯一编码")
    private String warehouseId;

    @ApiModelProperty(value = "仓库名称")
    private String warehouseName;

    @ApiModelProperty(value = "仓库类型(1--本地仓，2--海外仓，3--虚拟仓)")
    private String typeCd;

    @ApiModelProperty(value = "国家简称")
    private String countryCd;

    @ApiModelProperty(value = "省份名称")
    private String provinceName;

    @ApiModelProperty(value = "城市名称")
    private String cityName;

    @ApiModelProperty(value = "街道详细地址")
    private String detailsAddr;

    @ApiModelProperty(value = "邮编")
    private String zipCode;

    @ApiModelProperty(value = "仓库联系人")
    private String warehouseContact;

    @ApiModelProperty(value = "仓库联系电话")
    private String warehousePhone;

    @ApiModelProperty(value = "备注信息")
    private String remark;

    @ApiModelProperty(value = "是否分仓，0：未分仓，1：分仓")
    private int subWarehouseSupport;

    @ApiModelProperty(value = "发货地址")
    @Deprecated
    private String shippAddress;

    @ApiModelProperty(value = "仓库状态（1--正常，2--停用）")
    private int statusCd;

    @ApiModelProperty(value = "操作人")
    private String createUser;

    @ApiModelProperty(value = "操作时间")
    @JsonIgnore
    private Timestamp createTime;

    @ApiModelProperty(value = "最近修改人")
    private String modifyUser;

    @ApiModelProperty(value = "最近修改时间")
    private String modifyTime;

    @ApiModelProperty(value = "分仓列表信息")
    private List<WarehouseSeparateDetailsInfoEntity> subWarehouseList;


    public WarehouseManageInfoEntity() {
        this.subWarehouseList = new ArrayList<>();
    }

    public List<WarehouseSeparateDetailsInfoEntity> getSubWarehouseList() {
        return subWarehouseList;
    }

    public void setSubWarehouseList(List<WarehouseSeparateDetailsInfoEntity> subWarehouseList) {
        this.subWarehouseList = subWarehouseList;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getTypeCd() {
        return typeCd;
    }

    public void setTypeCd(String typeCd) {
        this.typeCd = typeCd;
    }

    public int getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(int statusCd) {
        this.statusCd = statusCd;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getShippAddress() {
        return shippAddress;
    }

    public void setShippAddress(String shippAddress) {
        this.shippAddress = shippAddress;
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

    public String getCountryCd() {
        return countryCd;
    }

    public void setCountryCd(String countryCd) {
        this.countryCd = countryCd;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getDetailsAddr() {
        return detailsAddr;
    }

    public void setDetailsAddr(String detailsAddr) {
        this.detailsAddr = detailsAddr;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getWarehouseContact() {
        return warehouseContact;
    }

    public void setWarehouseContact(String warehouseContact) {
        this.warehouseContact = warehouseContact;
    }

    public String getWarehousePhone() {
        return warehousePhone;
    }

    public void setWarehousePhone(String warehousePhone) {
        this.warehousePhone = warehousePhone;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getSubWarehouseSupport() {
        return subWarehouseSupport;
    }

    public void setSubWarehouseSupport(int subWarehouseSupport) {
        this.subWarehouseSupport = subWarehouseSupport;
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

    @Override
    public String toString() {
        return "WarehouseManageInfoEntity{" +
                "warehouseId='" + warehouseId + '\'' +
                ", warehouseName='" + warehouseName + '\'' +
                ", typeCd=" + typeCd +
                ", countryCd='" + countryCd + '\'' +
                ", provinceName='" + provinceName + '\'' +
                ", cityName='" + cityName + '\'' +
                ", detailsAddr='" + detailsAddr + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", warehouseContact='" + warehouseContact + '\'' +
                ", warehousePhone='" + warehousePhone + '\'' +
                ", remark='" + remark + '\'' +
                ", subWarehouseSupport=" + subWarehouseSupport +
                ", shippAddress='" + shippAddress + '\'' +
                ", statusCd=" + statusCd +
                ", createUser='" + createUser + '\'' +
                ", createTime=" + createTime +
                ", modifyUser='" + modifyUser + '\'' +
                ", modifyTime='" + modifyTime + '\'' +
                '}';
    }
}
