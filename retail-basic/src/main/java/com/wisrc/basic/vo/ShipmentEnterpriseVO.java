package com.wisrc.basic.vo;

import com.wisrc.basic.entity.ShipmentEnterpriseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@ApiModel(value = "物流商信息")
public class ShipmentEnterpriseVO {
    @ApiModelProperty(value = "物流商ID", hidden = true)
    private String shipmentId;

    @ApiModelProperty(value = "物流商名称", required = true)
    @NotEmpty(message = "物流商名称不能为空")
    private String shipmentName;

    @ApiModelProperty(value = "物流商地址", hidden = true)
    private String shipmentAddr;

    @ApiModelProperty(value = "物流商联系人")
    private String contact;

    @ApiModelProperty(value = "物流商电话")
    private String phone;

    @ApiModelProperty(value = "物流商QQ")
    private String qq;

    @ApiModelProperty(value = "物流商类型,(1--官方渠道，2--代理渠道)", required = true)
    @NotNull(message = "请选择物流类型")
    private int shipmentType;

    @ApiModelProperty(value = "物流商状态ID,(1--正常（默认），2--已删除)", hidden = true)
    private int statusCd;

    @ApiModelProperty(value = "修改时间", hidden = true)
    private String modifyTime;

    @ApiModelProperty(value = "国家名称", hidden = true)
    private String countryName;

    @ApiModelProperty(value = "国家编码", hidden = true)
    private String countryEn;

    @ApiModelProperty(value = "省编码", hidden = true)
    private String provinceEN;

    @ApiModelProperty(value = "省名称")
    private String provinceName;

    @ApiModelProperty(value = "城市编码", hidden = true)
    private String cityEn;

    @ApiModelProperty(value = "城市名称")
    private String cityName;

    @ApiModelProperty(value = "修改人", hidden = true)
    private String modifyUser;

    @ApiModelProperty(value = "创建时间", hidden = true)
    private String createTime;

    @ApiModelProperty(value = "创建人", hidden = true)
    private String createUser;

    public static final ShipmentEnterpriseVO toVO(ShipmentEnterpriseEntity ele) {
        ShipmentEnterpriseVO vo = new ShipmentEnterpriseVO();
        BeanUtils.copyProperties(ele, vo);
        return vo;
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

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
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

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
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

    public String getProvinceEN() {
        return provinceEN;
    }

    public void setProvinceEN(String provinceEN) {
        this.provinceEN = provinceEN;
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

    @Override
    public String toString() {
        return "ShipmentEnterpriseEntity{" +
                "shipmentId='" + shipmentId + '\'' +
                ", shipmentName='" + shipmentName + '\'' +
                ", shipmentAddr='" + shipmentAddr + '\'' +
                ", contact='" + contact + '\'' +
                ", phone='" + phone + '\'' +
                ", qq='" + qq + '\'' +
                ", statusCd=" + statusCd +
                ", modifyTime=" + modifyTime +
                ", modifyUser='" + modifyUser + '\'' +
                ", createTime=" + createTime +
                ", createUser='" + createUser + '\'' +
                '}';
    }
}
