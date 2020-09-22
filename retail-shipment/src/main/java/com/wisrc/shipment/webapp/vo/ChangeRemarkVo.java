package com.wisrc.shipment.webapp.vo;

import java.util.List;

public class ChangeRemarkVo {
    private String voucherCode;
    private String voucherType;
    private List<ChangeRemarkDetail> changeMarkList;

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

    public List<ChangeRemarkDetail> getChangeMarkList() {
        return changeMarkList;
    }

    public void setChangeMarkList(List<ChangeRemarkDetail> changeMarkList) {
        this.changeMarkList = changeMarkList;
    }
}
