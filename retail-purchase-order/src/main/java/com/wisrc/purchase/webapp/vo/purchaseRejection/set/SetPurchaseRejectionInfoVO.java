package com.wisrc.purchase.webapp.vo.purchaseRejection.set;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Data
@ApiModel
@Valid
public class SetPurchaseRejectionInfoVO {
    @NotBlank(message = "参数[rejectionId]为必填")
    @ApiModelProperty(value = "采购退货单ID")
    private String rejectionId;

    @NotBlank(message = "参数[rejectionDateStr]为必填")
    @ApiModelProperty(value = "拒收日期")
    private String rejectionDateStr;

    @NotBlank(message = "参数[handleUser]为必填")
    @ApiModelProperty(value = "处理人")
    private String handleUser;

    @NotBlank(message = "参数[inspectionId]为必填")
    @ApiModelProperty(value = "验货申请/提货单号")
    private String inspectionId;

    @NotBlank(message = "参数[supplierCd]为必填")
    @ApiModelProperty(value = "供应商编码")
    private String supplierCd;

    @ApiModelProperty(value = "供应商送货单")
    private String supplierDeliveryNum;

    @ApiModelProperty(value = "备注信息")
    private String remark;

    @NotBlank(message = "参数[orderId]为必填")
    @ApiModelProperty(value = "订单号")
    private String orderId;
}
