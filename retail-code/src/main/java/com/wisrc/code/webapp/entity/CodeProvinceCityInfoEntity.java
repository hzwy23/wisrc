package com.wisrc.code.webapp.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class CodeProvinceCityInfoEntity {
    private String uuid;

    private String provinceId;

    private String countryCd;

    private String provinceNameEn;

    private String provinceNameCn;

    private String cityNameCn;
    private String cityNameEn;

    @NotNull(message = "请填写类型，0：国家，1：省份，2：城市")
    private int typeCd;

    @ApiModelProperty(hidden = true)
    private String modifyUser;
    @ApiModelProperty(hidden = true)
    private String modifyTime;
    @ApiModelProperty(hidden = true)
    private String createUser;
    @ApiModelProperty(hidden = true)
    private String createTime;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getCountryCd() {
        return countryCd;
    }

    public void setCountryCd(String countryCd) {
        this.countryCd = countryCd;
    }

    public String getProvinceNameEn() {
        return provinceNameEn;
    }

    public void setProvinceNameEn(String provinceNameEn) {
        this.provinceNameEn = provinceNameEn;
    }

    public String getProvinceNameCn() {
        return provinceNameCn;
    }

    public void setProvinceNameCn(String provinceNameCn) {
        this.provinceNameCn = provinceNameCn;
    }

    public String getCityNameCn() {
        return cityNameCn;
    }

    public void setCityNameCn(String cityNameCn) {
        this.cityNameCn = cityNameCn;
    }

    public String getCityNameEn() {
        return cityNameEn;
    }

    public void setCityNameEn(String cityNameEn) {
        this.cityNameEn = cityNameEn;
    }

    public int getTypeCd() {
        return typeCd;
    }

    public void setTypeCd(int typeCd) {
        this.typeCd = typeCd;
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

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "CodeProvinceCityInfoEntity{" +
                "uuid='" + uuid + '\'' +
                ", countryCd='" + countryCd + '\'' +
                ", provinceNameEn='" + provinceNameEn + '\'' +
                ", provinceNameCn='" + provinceNameCn + '\'' +
                ", cityNameCn='" + cityNameCn + '\'' +
                ", cityNameEn='" + cityNameEn + '\'' +
                ", typeCd=" + typeCd +
                ", modifyUser='" + modifyUser + '\'' +
                ", modifyTime='" + modifyTime + '\'' +
                ", createUser='" + createUser + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }
}
