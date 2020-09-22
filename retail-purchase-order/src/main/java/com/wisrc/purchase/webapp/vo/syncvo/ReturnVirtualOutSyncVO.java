package com.wisrc.purchase.webapp.vo.syncvo;

import java.util.List;

public class ReturnVirtualOutSyncVO {

    private String voucherCode;

    private String voucherType;

    private String createTime;

    private String sectionCode;

    private String remark;

    List<ReturnVirtualOutDetailsVO> goodsList;

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

    public List<ReturnVirtualOutDetailsVO> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<ReturnVirtualOutDetailsVO> goodsList) {
        this.goodsList = goodsList;
    }
}
