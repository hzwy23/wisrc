package com.wisrc.purchase.webapp.vo;

import javax.validation.constraints.NotEmpty;

public class RemarkVo {
    private String remark;
    @NotEmpty(message = "orderId不能为空")
    private String orderId;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
