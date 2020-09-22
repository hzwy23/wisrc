package com.wisrc.purchase.webapp.vo.purchaseReturn.set;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel
public class SetPurchaseReturnInfoVO {
    @NotBlank(message = "参数[returnBill]为必填")
    @ApiModelProperty(value = "退货单号", required = true)
    private String returnBill;

    @NotBlank(message = "参数[createDateStr]为必填")
    @ApiModelProperty(value = "拒收日期", required = true)
    private String createDateStr;

    @NotBlank(message = "参数[supplierId]为必填")
    @ApiModelProperty(value = "供应商编码", required = true)
    private String supplierId;

    @NotBlank(message = "参数[employeeId]为必填")
    @ApiModelProperty(value = "操作人处理人", required = true)
    private String employeeId;

    @NotBlank(message = "参数[warehouseId]为必填")
    @ApiModelProperty(value = "仓库ID", required = true)
    private String warehouseId;

    @NotBlank(message = "参数[orderId]为必填")
    @ApiModelProperty(value = "订单ID", required = true)
    private String orderId;

    @ApiModelProperty(value = "备注信息")
    private String remark;

    @ApiModelProperty(value = "包材仓库")
    private String packWarehouseId;

}
