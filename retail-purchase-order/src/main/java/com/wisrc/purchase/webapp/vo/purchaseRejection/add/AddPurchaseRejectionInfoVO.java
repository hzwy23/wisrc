package com.wisrc.purchase.webapp.vo.purchaseRejection.add;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Data
@ApiModel
@Valid
public class AddPurchaseRejectionInfoVO {

    @NotBlank(message = "参数[rejectionDateStr]为必填")
    @ApiModelProperty(value = "拒收日期", required = true)
    private String rejectionDateStr;

    @NotBlank(message = "参数[handleUser]为必填")
    @ApiModelProperty(value = "处理人", required = true)
    private String handleUser;

    @NotBlank(message = "参数[inspectionId]为必填")
    @ApiModelProperty(value = "验货申请/提货单号", required = true)
    private String inspectionId;

    @NotBlank(message = "参数[supplierCd]为必填")
    @ApiModelProperty(value = "供应商编码", required = true)
    private String supplierCd;

    @ApiModelProperty(value = "供应商送货单")
    private String supplierDeliveryNum;

    @ApiModelProperty(value = "备注信息")
    private String remark;

    @NotBlank(message = "参数[orderId]为必填")
    @ApiModelProperty(value = "采购订单号", required = true)
    private String orderId;
}
