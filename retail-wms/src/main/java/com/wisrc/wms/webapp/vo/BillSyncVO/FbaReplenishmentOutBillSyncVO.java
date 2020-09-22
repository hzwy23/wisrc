package com.wisrc.wms.webapp.vo.BillSyncVO;


import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class FbaReplenishmentOutBillSyncVO {
    @ApiModelProperty(value = "出库单号")
    private String voucherCode;
    @ApiModelProperty(value = "单据类型")
    private String voucherType;
    @ApiModelProperty(value = "分仓编号")
    private String sectionCode;
    @ApiModelProperty(value = "单据日期")
    private String createTime;
    @ApiModelProperty(value = "fba补货单补货类型")
    private String voucherCat;
    @ApiModelProperty(value = "创建人")
    private String applicant;
    @ApiModelProperty(value = "补货批次")
    private String replenishmentBatch;
    @ApiModelProperty(value = "收货地址")
    private String address;
    @ApiModelProperty(value = "Reference ID")
    private String referenceId;
    @ApiModelProperty(value = "目的仓，FBA补货时传递店铺")
    private String targetWhName;
    @ApiModelProperty(value = "备注")
    private String remark;
    @ApiModelProperty(value = "package箱唛")
    private String boxInfoUrl;
    @ApiModelProperty(value = "packlist装箱清单")
    private String boxGaugeUrl;

    @ApiModelProperty(value = "产品列表")
    private List<FbaGoodsInfoVO> goodsList;

    public String getVoucherCat() {
        return voucherCat;
    }

    public void setVoucherCat(String voucherCat) {
        this.voucherCat = voucherCat;
    }

    public String getVoucherCode() {
        return voucherCode;
    }

    public void setVoucherCode(String voucherCode) {
        this.voucherCode = voucherCode;
    }

    public String getVoucherType() {
        return voucherType;
    }

    public void setVoucherType(String voucherType) {
        this.voucherType = voucherType;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getSectionCode() {
        return sectionCode;
    }

    public void setSectionCode(String sectionCode) {
        this.sectionCode = sectionCode;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getApplicant() {
        return applicant;
    }

    public void setApplicant(String applicant) {
        this.applicant = applicant;
    }

    public String getReplenishmentBatch() {
        return replenishmentBatch;
    }

    public void setReplenishmentBatch(String replenishmentBatch) {
        this.replenishmentBatch = replenishmentBatch;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    public String getTargetWhName() {
        return targetWhName;
    }

    public void setTargetWhName(String targetWhName) {
        this.targetWhName = targetWhName;
    }

    public String getBoxInfoUrl() {
        return boxInfoUrl;
    }

    public void setBoxInfoUrl(String boxInfoUrl) {
        this.boxInfoUrl = boxInfoUrl;
    }

    public String getBoxGaugeUrl() {
        return boxGaugeUrl;
    }

    public void setBoxGaugeUrl(String boxGaugeUrl) {
        this.boxGaugeUrl = boxGaugeUrl;
    }

    public List<FbaGoodsInfoVO> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<FbaGoodsInfoVO> goodsList) {
        this.goodsList = goodsList;
    }
}
