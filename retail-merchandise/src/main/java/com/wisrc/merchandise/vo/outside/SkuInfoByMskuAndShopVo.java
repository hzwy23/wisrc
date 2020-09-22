package com.wisrc.merchandise.vo.outside;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Map;

public class SkuInfoByMskuAndShopVo {
    @ApiModelProperty("商品唯一标识")
    private String commodityId;
    @ApiModelProperty("商品编码")
    private String mskuId;
    @ApiModelProperty("商品名称")
    private String mskuName;
    @ApiModelProperty("产品唯一编码")
    private String skuId;
    @ApiModelProperty("产品名称")
    private String skuName;
    @ApiModelProperty("fnsku")
    private String fnSkuId;
    @ApiModelProperty("asin")
    private String asin;
    @ApiModelProperty("产品的特性标签")
    private List<String> lables;
    @ApiModelProperty("负责人id")
    private String chargePersonId;
    @ApiModelProperty("负责人名称")
    private String chargePerson;
    @ApiModelProperty("商品销售状态")
    private String saleStatus;
    @ApiModelProperty("产品的装箱信息")
    private Map skuPackInfo;
    @ApiModelProperty("产品图片")
    private List<String> imgs;

    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
    }

    public List<String> getImgs() {
        return imgs;
    }

    public void setImgs(List<String> imgs) {
        this.imgs = imgs;
    }

    public String getChargePersonId() {
        return chargePersonId;
    }

    public void setChargePersonId(String chargePersonId) {
        this.chargePersonId = chargePersonId;
    }

    public String getMskuId() {
        return mskuId;
    }

    public void setMskuId(String mskuId) {
        this.mskuId = mskuId;
    }

    public String getMskuName() {
        return mskuName;
    }

    public void setMskuName(String mskuName) {
        this.mskuName = mskuName;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getFnSkuId() {
        return fnSkuId;
    }

    public void setFnSkuId(String fnSkuId) {
        this.fnSkuId = fnSkuId;
    }

    public String getAsin() {
        return asin;
    }

    public void setAsin(String asin) {
        this.asin = asin;
    }

    public List<String> getLables() {
        return lables;
    }

    public void setLables(List<String> lables) {
        this.lables = lables;
    }

    public String getChargePerson() {
        return chargePerson;
    }

    public void setChargePerson(String chargePerson) {
        this.chargePerson = chargePerson;
    }

    public String getSaleStatus() {
        return saleStatus;
    }

    public void setSaleStatus(String saleStatus) {
        this.saleStatus = saleStatus;
    }

    public Map getSkuPackInfo() {
        return skuPackInfo;
    }

    public void setSkuPackInfo(Map skuPackInfo) {
        this.skuPackInfo = skuPackInfo;
    }
}
