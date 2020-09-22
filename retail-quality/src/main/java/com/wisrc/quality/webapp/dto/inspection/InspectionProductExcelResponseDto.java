package com.wisrc.quality.webapp.dto.inspection;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "验货保存表单")
public class InspectionProductExcelResponseDto {
    @ApiModelProperty(value = "验货申请/提货单号")
    private String inspectionId;

    @ApiModelProperty(value = "单据日期")
    private String applyDate;

    @ApiModelProperty(value = "申请人")
    private String employeeName;

    @ApiModelProperty(value = "预计验货时间")
    private String expectInspectionTime;

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

    @ApiModelProperty(value = "验货方式")
    private String inspectionType;

    @ApiModelProperty(value = "状态")
    private String status;
}
