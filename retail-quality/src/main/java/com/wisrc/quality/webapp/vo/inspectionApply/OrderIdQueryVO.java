package com.wisrc.quality.webapp.vo.inspectionApply;

import io.swagger.annotations.ApiModelProperty;

import java.sql.Date;

//外部接口调用出参实体
public class OrderIdQueryVO {

    @ApiModelProperty(value = "采购订单id")
    private String orderId;
    @ApiModelProperty(value = "产品id")
    private String skuId;
    @ApiModelProperty(value = "验货申请单号")
    private String inspectionId;
    @ApiModelProperty(value = "验货申请数量")
    private int applyInspectionQuantity;
    @ApiModelProperty(value = "验货方式")
    private int inspectionTypeCd;
    @ApiModelProperty(value = "合格数量")
    private int qualifiedQuantity;
    @ApiModelProperty(value = "申请日期")
    private Date applyDate;

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

    public int getApplyInspectionQuantity() {
        return applyInspectionQuantity;
    }

    public void setApplyInspectionQuantity(int applyInspectionQuantity) {
        this.applyInspectionQuantity = applyInspectionQuantity;
    }

    public int getInspectionTypeCd() {
        return inspectionTypeCd;
    }

    public void setInspectionTypeCd(int inspectionTypeCd) {
        this.inspectionTypeCd = inspectionTypeCd;
    }

    public int getQualifiedQuantity() {
        return qualifiedQuantity;
    }

    public void setQualifiedQuantity(int qualifiedQuantity) {
        this.qualifiedQuantity = qualifiedQuantity;
    }

    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }
}
