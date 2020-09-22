package com.wisrc.quality.webapp.vo.inspectionApply;

import io.swagger.annotations.ApiModelProperty;

//获取完工数，计算总和出参
public class OrderIdQuerySumVO {

    @ApiModelProperty(value = "采购订单id")
    private String orderId;
    @ApiModelProperty(value = "产品id")
    private String skuId;
    @ApiModelProperty(value = "申请验货单号")
    private String inspectionId;
    @ApiModelProperty(value = "完工数")
    private int completeNum;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getInspectionId() {
        return inspectionId;
    }

    public void setInspectionId(String inspectionId) {
        this.inspectionId = inspectionId;
    }

    public int getCompleteNum() {
        return completeNum;
    }

    public void setCompleteNum(int completeNum) {
        this.completeNum = completeNum;
    }
}
