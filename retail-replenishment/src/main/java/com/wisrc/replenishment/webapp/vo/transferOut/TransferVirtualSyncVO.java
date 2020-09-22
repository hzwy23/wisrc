package com.wisrc.replenishment.webapp.vo.transferOut;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class TransferVirtualSyncVO {

    @ApiModelProperty(value = "调拨单号")
    private String voucherCode;

    @ApiModelProperty(value = "单据类型")
    private String voucherType;

    @ApiModelProperty(value = "分仓编号")
    private String sectionCode;

    @ApiModelProperty(value = "备注")
    private String remark;

    List<TransferVirvualDetailSyncVO> goodsList;

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

    public List<TransferVirvualDetailSyncVO> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<TransferVirvualDetailSyncVO> goodsList) {
        this.goodsList = goodsList;
    }
}
