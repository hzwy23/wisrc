package com.wisrc.purchase.webapp.dto.inspection;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "获取验货表单所有产品")
public class GetInspectionProductDto {
    @ApiModelProperty(value = "到货产品ID")
    private String arrivalProductId;

    @ApiModelProperty(value = "SKU")
    private String skuId;

    @ApiModelProperty(value = "产品中文名")
    private String productName;

    @ApiModelProperty(value = "入库欠数")
    private Integer warehouseOweNum;

    @ApiModelProperty(value = "提货数量")
    private Integer deliveryQuantity;

    @ApiModelProperty(value = "提备品数")
    private Integer deliverySpareQuantity;

    @ApiModelProperty(value = "运费")
    private String freight;

    @ApiModelProperty(value = "验货数量")
    private Integer inspectionQuantity;

    @ApiModelProperty(value = "合格数")
    private Integer qualifiedQualified;

    @ApiModelProperty(value = "不合格数")
    private Integer unqualifiedQuantity;

    @ApiModelProperty(value = "收货数")
    private Integer receiptQuantity;

    @ApiModelProperty(value = "收备品数")
    private Integer receiptSpareQuantity;

    @ApiModelProperty(value = "箱数")
    private Integer cartonNum;

    @ApiModelProperty(value = "尾数")
    private Integer mantissaNum;

    @ApiModelProperty(value = "备品率(%)")
    private Double spareRate;

    @ApiModelProperty(value = "体积(m3)")
    private Double packVolume;

    @ApiModelProperty(value = "毛重(kg)")
    private Double grossWeight;
    @ApiModelProperty(value = "到货通知单状态码")
    private Integer status;

    public String getArrivalProductId() {
        return arrivalProductId;
    }

    public void setArrivalProductId(String arrivalProductId) {
        this.arrivalProductId = arrivalProductId;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getWarehouseOweNum() {
        return warehouseOweNum;
    }

    public void setWarehouseOweNum(Integer warehouseOweNum) {
        this.warehouseOweNum = warehouseOweNum;
    }

    public Integer getDeliveryQuantity() {
        return deliveryQuantity;
    }

    public void setDeliveryQuantity(Integer deliveryQuantity) {
        this.deliveryQuantity = deliveryQuantity;
    }

    public Integer getDeliverySpareQuantity() {
        return deliverySpareQuantity;
    }

    public void setDeliverySpareQuantity(Integer deliverySpareQuantity) {
        this.deliverySpareQuantity = deliverySpareQuantity;
    }

    public String getFreight() {
        return freight;
    }

    public void setFreight(String freight) {
        this.freight = freight;
    }

    public Integer getInspectionQuantity() {
        return inspectionQuantity;
    }

    public void setInspectionQuantity(Integer inspectionQuantity) {
        this.inspectionQuantity = inspectionQuantity;
    }

    public Integer getQualifiedQualified() {
        return qualifiedQualified;
    }

    public void setQualifiedQualified(Integer qualifiedQualified) {
        this.qualifiedQualified = qualifiedQualified;
    }

    public Integer getUnqualifiedQuantity() {
        return unqualifiedQuantity;
    }

    public void setUnqualifiedQuantity(Integer unqualifiedQuantity) {
        this.unqualifiedQuantity = unqualifiedQuantity;
    }

    public Integer getReceiptQuantity() {
        return receiptQuantity;
    }

    public void setReceiptQuantity(Integer receiptQuantity) {
        this.receiptQuantity = receiptQuantity;
    }

    public Integer getReceiptSpareQuantity() {
        return receiptSpareQuantity;
    }

    public void setReceiptSpareQuantity(Integer receiptSpareQuantity) {
        this.receiptSpareQuantity = receiptSpareQuantity;
    }

    public Integer getCartonNum() {
        return cartonNum;
    }

    public void setCartonNum(Integer cartonNum) {
        this.cartonNum = cartonNum;
    }

    public Integer getMantissaNum() {
        return mantissaNum;
    }

    public void setMantissaNum(Integer mantissaNum) {
        this.mantissaNum = mantissaNum;
    }

    public Double getSpareRate() {
        return spareRate;
    }

    public void setSpareRate(Double spareRate) {
        this.spareRate = spareRate;
    }

    public Double getPackVolume() {
        return packVolume;
    }

    public void setPackVolume(Double packVolume) {
        this.packVolume = packVolume;
    }

    public Double getGrossWeight() {
        return grossWeight;
    }

    public void setGrossWeight(Double grossWeight) {
        this.grossWeight = grossWeight;
    }
}
