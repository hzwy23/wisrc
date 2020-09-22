package com.wisrc.replenishment.webapp.vo.waybill;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

@Api(tags = "签收数量")
public class WaybillMskuNumVO {
    @ApiModelProperty(value = "补货详细商品装箱信息UUID")
    private String uuid;
    @ApiModelProperty(value = "签收数量")
    private int signInQuantity;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getSignInQuantity() {
        return signInQuantity;
    }

    public void setSignInQuantity(int signInQuantity) {
        this.signInQuantity = signInQuantity;
    }
}
