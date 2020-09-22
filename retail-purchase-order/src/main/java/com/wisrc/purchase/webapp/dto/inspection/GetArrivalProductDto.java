package com.wisrc.purchase.webapp.dto.inspection;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GetArrivalProductDto {
    @ApiModelProperty(value = "提货数量")
    private Integer deliveryQuantity;
    @ApiModelProperty(value = "到货通知单号")
    private String arrivalId;
    @ApiModelProperty(value = "发起人")
    private String employeeName;
    @ApiModelProperty(value = "预计到货时间")
    private String expectArrivalTime;
    @ApiModelProperty(value = "供应商编号")
    private String supplierId;
    @ApiModelProperty(value = "供应商名称")
    private String supplierName;
    @ApiModelProperty(value = "SKU")
    private String skuId;
    @ApiModelProperty(value = "产品中文名")
    private String productName;
    @ApiModelProperty(value = "采购订单号")
    private String orderId;
    @ApiModelProperty(value = "到货产品Id")
    private String arrivalProductId;

    @ApiModelProperty(value = "收获数量")
    private Integer receiptQuantity;

    @ApiModelProperty(value = "收备品数")
    private Integer receiptSpareQuantity;
}
