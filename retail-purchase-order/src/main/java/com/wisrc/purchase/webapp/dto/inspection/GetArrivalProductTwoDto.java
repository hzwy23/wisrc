package com.wisrc.purchase.webapp.dto.inspection;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "获取验货表单所有产品")
public class GetArrivalProductTwoDto {
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
    private String status;
}
