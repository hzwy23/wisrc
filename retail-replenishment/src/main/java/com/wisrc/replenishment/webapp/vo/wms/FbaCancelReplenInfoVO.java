package com.wisrc.replenishment.webapp.vo.wms;

import io.swagger.annotations.ApiModelProperty;

public class FbaCancelReplenInfoVO {

    @ApiModelProperty(value = "单据编号")
    private String voucherCode;

    @ApiModelProperty(value = "单据类型", notes = "XNCK:销售出库原因;FBABH")
    private String voucherType;

    @ApiModelProperty(value = "取消原因")
    private String cancelReason;

    @ApiModelProperty(value = "取消时间")
    private String cancelTime;

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

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    public String getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(String cancelTime) {
        this.cancelTime = cancelTime;
    }
}
