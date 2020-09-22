package com.wisrc.purchase.webapp.vo.purchaseRejection.get;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;

@Data
@ApiModel
@Valid
public class GetPurchaseRejectionVO {
    @ApiModelProperty(value = "采购拒收单号")
    private String rejectionId;

    @ApiModelProperty(value = "开始日期")
    private String rejectionDateStartStr;

    @ApiModelProperty(value = "结束日期")
    private String rejectionDateEndStr;

    @ApiModelProperty(value = "供应商编码")
    private String supplierCd;

    @ApiModelProperty(value = "验货申请/提货单号")
    private String inspectionId;

    @ApiModelProperty(value = "供应商送货单")
    private String supplierDeliveryNum;

    @ApiModelProperty(value = "处理人")
    private String handleUser;

    @ApiModelProperty(value = "拒收单状态")
    private Integer statusCd;
}
