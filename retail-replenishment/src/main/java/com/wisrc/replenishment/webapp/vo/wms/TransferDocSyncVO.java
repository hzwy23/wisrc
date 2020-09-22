package com.wisrc.replenishment.webapp.vo.wms;

import io.swagger.annotations.ApiModelProperty;

public class TransferDocSyncVO {
    @ApiModelProperty(value = "调拨单号")
    private String voucherCode;
    @ApiModelProperty(value = "报关资料")
    private String customsInfoUrl;
    @ApiModelProperty(value = "物流面单")
    private String logisticsInfoUrl;
    @ApiModelProperty(value = "物流商名称")
    private String logisticsName;
    @ApiModelProperty(value = "S/O No.")
    private String soNo;
    @ApiModelProperty(value = "发货仓-街道地址")
    private String whTown;
    @ApiModelProperty(value = "发货仓-省份/州")
    private String whProvince;
    @ApiModelProperty(value = "发货仓-城市")
    private String whCity;
    @ApiModelProperty(value = "发货仓-邮编")
    private String whZipCode;
    @ApiModelProperty(value = "收货仓")
    private String targetWhName;
    @ApiModelProperty(value = "总箱数")
    private int packageTotal;

    public String getVoucherCode() {
        return voucherCode;
    }

    public void setVoucherCode(String voucherCode) {
        this.voucherCode = voucherCode;
    }

    public String getCustomsInfoUrl() {
        return customsInfoUrl;
    }

    public void setCustomsInfoUrl(String customsInfoUrl) {
        this.customsInfoUrl = customsInfoUrl;
    }

    public String getLogisticsInfoUrl() {
        return logisticsInfoUrl;
    }

    public void setLogisticsInfoUrl(String logisticsInfoUrl) {
        this.logisticsInfoUrl = logisticsInfoUrl;
    }

    public String getLogisticsName() {
        return logisticsName;
    }

    public void setLogisticsName(String logisticsName) {
        this.logisticsName = logisticsName;
    }

    public String getSoNo() {
        return soNo;
    }

    public void setSoNo(String soNo) {
        this.soNo = soNo;
    }

    public String getWhTown() {
        return whTown;
    }

    public void setWhTown(String whTown) {
        this.whTown = whTown;
    }

    public String getWhProvince() {
        return whProvince;
    }

    public void setWhProvince(String whProvince) {
        this.whProvince = whProvince;
    }

    public String getWhCity() {
        return whCity;
    }

    public void setWhCity(String whCity) {
        this.whCity = whCity;
    }

    public String getWhZipCode() {
        return whZipCode;
    }

    public void setWhZipCode(String whZipCode) {
        this.whZipCode = whZipCode;
    }

    public String getTargetWhName() {
        return targetWhName;
    }

    public void setTargetWhName(String targetWhName) {
        this.targetWhName = targetWhName;
    }

    public int getPackageTotal() {
        return packageTotal;
    }

    public void setPackageTotal(int packageTotal) {
        this.packageTotal = packageTotal;
    }
}
