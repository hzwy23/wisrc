package com.wisrc.basic.entity;

import com.wisrc.basic.utils.Time;
import io.swagger.annotations.ApiModelProperty;

import java.sql.Timestamp;

public class ShipmentEnterpriseEntity {
    @ApiModelProperty(value = "物流商ID", required = true)
    private String shipmentId;
    @ApiModelProperty(value = "物流商名称")
    private String shipmentName;

    private String countryName;
    private String countryEn;
    private String provinceEn;
    private String provinceName;
    private String cityEn;
    private String cityName;

    @ApiModelProperty(value = "物流商地址")
    private String shipmentAddr;
    @ApiModelProperty(value = "物流商联系人")
    private String contact;
    @ApiModelProperty(value = "物流商电话")
    private String phone;
    @ApiModelProperty(value = "物流商QQ")
    private String qq;
    @ApiModelProperty(value = "物流商类型,(1--官方渠道，2--代理渠道)", required = true)
    private int shipmentType;
    @ApiModelProperty(value = "物流商状态ID,(1--正常（默认），2--已删除)", required = true)
    private int statusCd;
    @ApiModelProperty(value = "更新时间", hidden = true)
    private String modifyTime;
    @ApiModelProperty(value = "更新人", hidden = true)
    private String modifyUser;
    @ApiModelProperty(value = "创建时间", hidden = true)
    private String createTime;
    @ApiModelProperty(value = "创建人", hidden = true)
    private String createUser;


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

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryEn() {
        return countryEn;
    }

    public void setCountryEn(String countryEn) {
        this.countryEn = countryEn;
    }

    public String getProvinceEn() {
        return provinceEn;
    }

    public void setProvinceEn(String provinceEn) {
        this.provinceEn = provinceEn;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityEn() {
        return cityEn;
    }

    public void setCityEn(String cityEn) {
        this.cityEn = cityEn;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getShipmentAddr() {
        return shipmentAddr;
    }

    public void setShipmentAddr(String shipmentAddr) {
        this.shipmentAddr = shipmentAddr;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public int getShipmentType() {
        return shipmentType;
    }

    public void setShipmentType(int shipmentType) {
        this.shipmentType = shipmentType;
    }

    public int getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(int statusCd) {
        this.statusCd = statusCd;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Timestamp modifyTime) {
        this.modifyTime = Time.formatDateTime(modifyTime);
    }

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = Time.formatDateTime(createTime);
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }


    @Override
    public String toString() {
        return "ShipmentEnterpriseEntity{" +
                "shipmentId='" + shipmentId + '\'' +
                ", shipmentName='" + shipmentName + '\'' +
                ", countryName='" + countryName + '\'' +
                ", countryEn='" + countryEn + '\'' +
                ", provinceEn='" + provinceEn + '\'' +
                ", provinceName='" + provinceName + '\'' +
                ", cityEn='" + cityEn + '\'' +
                ", cityName='" + cityName + '\'' +
                ", shipmentAddr='" + shipmentAddr + '\'' +
                ", contact='" + contact + '\'' +
                ", phone='" + phone + '\'' +
                ", qq='" + qq + '\'' +
                ", shipmentType=" + shipmentType +
                ", statusCd=" + statusCd +
                ", modifyTime='" + modifyTime + '\'' +
                ", modifyUser='" + modifyUser + '\'' +
                ", createTime='" + createTime + '\'' +
                ", createUser='" + createUser + '\'' +
                '}';
    }
}
