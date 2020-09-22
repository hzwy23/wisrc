package com.wisrc.wms.webapp.vo.BillSyncVO;

import com.wisrc.wms.webapp.vo.GoodsInfoVO;

import java.util.List;

public class PurchaseOrderVO {
    private String voucherCode;
    private String voucherType;
    private String supplierCode;
    private String supplierName;
    private String createTime;
    private String whCode;
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

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getWhCode() {
        return whCode;
    }

    public void setWhCode(String whCode) {
        this.whCode = whCode;
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
