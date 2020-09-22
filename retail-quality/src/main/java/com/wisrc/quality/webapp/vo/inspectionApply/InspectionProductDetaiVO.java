package com.wisrc.quality.webapp.vo.inspectionApply;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class InspectionProductDetaiVO {
    @ApiModelProperty(value = "SKU")
    private String inspectionProductId;
    @ApiModelProperty(value = "SKU")
    private String skuId;
    @ApiModelProperty(value = "产品中文名")
    private String skuName;
    @ApiModelProperty(value = "订单数量")
    private Integer quantity;
    @ApiModelProperty(value = "备品率(%)")
    private Double spareRate;
    @ApiModelProperty(value = "订单总数")
    private Double quantityTotal;
    @ApiModelProperty(value = "验货申请数")
    private int applyInspectionQuantity;
    @ApiModelProperty(value = "产品检验单号")
    private String inspectionCd;//产品检验单号
    @ApiModelProperty(value = "验货数量")
    private int inspectionQuantity;
    @ApiModelProperty(value = "合格数")
    private int qualifiedQuantity;
    @ApiModelProperty(value = "不合格数")
    private int unqualifiedQuantity;
    @ApiModelProperty(value = "状态编码")
    private int statusCd;
    @ApiModelProperty(value = "状态描述")
    private String statusDesc;

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public int getApplyInspectionQuantity() {
        return applyInspectionQuantity;
    }

    public void setApplyInspectionQuantity(int applyInspectionQuantity) {
        this.applyInspectionQuantity = applyInspectionQuantity;
    }

    public String getInspectionCd() {
        return inspectionCd;
    }

    public void setInspectionCd(String inspectionCd) {
        this.inspectionCd = inspectionCd;
    }

    public int getInspectionQuantity() {
        return inspectionQuantity;
    }

    public void setInspectionQuantity(int inspectionQuantity) {
        this.inspectionQuantity = inspectionQuantity;
    }

    public int getQualifiedQuantity() {
        return qualifiedQuantity;
    }

    public void setQualifiedQuantity(int qualifiedQuantity) {
        this.qualifiedQuantity = qualifiedQuantity;
    }

    public int getUnqualifiedQuantity() {
        return unqualifiedQuantity;
    }

    public void setUnqualifiedQuantity(int unqualifiedQuantity) {
        this.unqualifiedQuantity = unqualifiedQuantity;
    }

    public int getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(int statusCd) {
        this.statusCd = statusCd;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }
}
