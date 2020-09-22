package com.wisrc.warehouse.webapp.vo.syncVO;

import java.util.List;

public class VirtualEnterBillSyncVO {
    private String voucherCode;
    private String voucherType;
    private String createTime;
    private String sectionCode;
    private String remark;
    private List<GoodsInfoVO> goodsList;

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

    public List<GoodsInfoVO> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<GoodsInfoVO> goodsList) {
        this.goodsList = goodsList;
    }
}
