package com.wisrc.code.webapp.entity;

public class AdministrativeDivisionInfoEntity {
    private String uuid;
    private String countryCd;
    private String countryName;
    private String countryEnglish;
    private String provinceNameEn;
    private String provinceNameCn;
    private String cityNameCn;
    private String cityNameEn;
    private int typeCd;
    private String typeDesc;
    private String modifyUser;
    private String modifyTime;
    private String createUser;
    private String createTime;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getCountryCd() {
        return countryCd;
    }

    public void setCountryCd(String countryCd) {
        this.countryCd = countryCd;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryEnglish() {
        return countryEnglish;
    }

    public void setCountryEnglish(String countryEnglish) {
        this.countryEnglish = countryEnglish;
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

    public String getTypeDesc() {
        return typeDesc;
    }

    public void setTypeDesc(String typeDesc) {
        this.typeDesc = typeDesc;
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
        return "AdministrativeDivisionInfoEntity{" +
                "uuid='" + uuid + '\'' +
                ", countryCd='" + countryCd + '\'' +
                ", countryName='" + countryName + '\'' +
                ", countryEnglish='" + countryEnglish + '\'' +
                ", provinceNameEn='" + provinceNameEn + '\'' +
                ", provinceNameCn='" + provinceNameCn + '\'' +
                ", cityNameCn='" + cityNameCn + '\'' +
                ", cityNameEn='" + cityNameEn + '\'' +
                ", typeCd=" + typeCd +
                ", typeDesc='" + typeDesc + '\'' +
                ", modifyUser='" + modifyUser + '\'' +
                ", modifyTime='" + modifyTime + '\'' +
                ", createUser='" + createUser + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }
}
