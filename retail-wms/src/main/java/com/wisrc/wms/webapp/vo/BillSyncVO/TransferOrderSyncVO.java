package com.wisrc.wms.webapp.vo.BillSyncVO;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class TransferOrderSyncVO {

    @ApiModelProperty(value = "单据编号")
    private String voucherCode;

    @ApiModelProperty(value = "单据类型", notes = "默认: DB")
    private String voucherType;

    @ApiModelProperty(value = "分仓编号")
    private String sectionCode;

    @ApiModelProperty(value = "备注")
    private String remark;

    List<TransferOrderProductSyncVO> goodsList;

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

    public List<TransferOrderProductSyncVO> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<TransferOrderProductSyncVO> goodsList) {
        this.goodsList = goodsList;
    }
}
