package com.wisrc.quality.webapp.vo.inspectionApply;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;

public class InspectionProdAddVO {

    @ApiModelProperty(value = "产品id")
    @NotEmpty(message = "产品id不能为空")
    private String skuId;
    @ApiModelProperty(value = "申请验货数量")
    private int applyInspectionQuantity;

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public int getApplyInspectionQuantity() {
        return applyInspectionQuantity;
    }

    public void setApplyInspectionQuantity(int applyInspectionQuantity) {
        this.applyInspectionQuantity = applyInspectionQuantity;
    }
}
