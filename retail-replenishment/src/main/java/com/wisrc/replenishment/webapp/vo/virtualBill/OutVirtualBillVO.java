package com.wisrc.replenishment.webapp.vo.virtualBill;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class OutVirtualBillVO {

    List<OutVirtualBillDetailsVO> goodsList;
    @ApiModelProperty("单据编号")
    private String voucherCode;
    @ApiModelProperty(value = "单据类型", notes = "类型：FBH")
    private String voucherType;
    @ApiModelProperty(value = "仓库编码")
    private String sectionCode;
    @ApiModelProperty(value = "备注")
    private String remark;

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

    public List<OutVirtualBillDetailsVO> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<OutVirtualBillDetailsVO> goodsList) {
        this.goodsList = goodsList;
    }
}
