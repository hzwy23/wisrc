package com.wisrc.purchase.webapp.vo.inspection;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@ApiModel(description = "验货单产品")
public class InspectionProductVo {
    @ApiModelProperty(value = "SKU", required = true)
    @NotEmpty(message = "sku不能为空")
    private String skuId;

    @ApiModelProperty(value = "入库欠数", required = false)
    private Integer warehouseOweNum;

    @ApiModelProperty(value = "收货数量", required = false)
    private Integer receiptQuantity;

    @ApiModelProperty(value = "收备品数", required = false)
    private Integer receiptSpareQuantity;

    @ApiModelProperty(value = "提货数量", required = false)
    @NotNull
    @Min(value = 0, message = "提货数量为非负整数")
    private Integer deliveryQuantity;

    @ApiModelProperty(value = "提备品数", required = false)
    @Min(value = 0, message = "提备品数为非负整数")
    private Integer deliverySpareQuantity;

    @ApiModelProperty(value = "运费", required = false)
    //@PositiveMaxDouble(message = "运费必须为2位小数以内数字")
    private String freight;

    @ApiModelProperty(value = "箱数", required = false)
    private Integer cartonNum;

    @ApiModelProperty(value = "尾数", required = false)
    private Integer mantissaNum;
}
