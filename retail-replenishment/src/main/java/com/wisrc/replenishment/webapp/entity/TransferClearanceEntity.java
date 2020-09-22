package com.wisrc.replenishment.webapp.entity;

import io.swagger.annotations.ApiModelProperty;

public class TransferClearanceEntity {
    @ApiModelProperty("原产地")
    private String countryOfOrigin;
    @ApiModelProperty("材质")
    private String textureOfMateria;
    @ApiModelProperty("用途")
    private String purposeDesc;
    @ApiModelProperty("清关名称")
    private String clearanceName;
    @ApiModelProperty("申报单价")
    private double clearanceUnitPrice;

    public String getCountryOfOrigin() {
        return countryOfOrigin;
    }

    public void setCountryOfOrigin(String countryOfOrigin) {
        this.countryOfOrigin = countryOfOrigin;
    }

    public String getTextureOfMateria() {
        return textureOfMateria;
    }

    public void setTextureOfMateria(String textureOfMateria) {
        this.textureOfMateria = textureOfMateria;
    }

    public String getPurposeDesc() {
        return purposeDesc;
    }

    public void setPurposeDesc(String purposeDesc) {
        this.purposeDesc = purposeDesc;
    }

    public String getClearanceName() {
        return clearanceName;
    }

    public void setClearanceName(String clearanceName) {
        this.clearanceName = clearanceName;
    }

    public double getClearanceUnitPrice() {
        return clearanceUnitPrice;
    }

    public void setClearanceUnitPrice(double clearanceUnitPrice) {
        this.clearanceUnitPrice = clearanceUnitPrice;
    }
}
