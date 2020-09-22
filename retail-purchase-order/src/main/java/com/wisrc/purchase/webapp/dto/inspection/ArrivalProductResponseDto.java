package com.wisrc.purchase.webapp.dto.inspection;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "到货保存表单")
public class ArrivalProductResponseDto {
    @ApiModelProperty(value = "到货通知单号")
    private String arrivalId;
    @ApiModelProperty(value = "录单日期")
    private String applyDate;
    @ApiModelProperty(value = "发起人")
    private String employeeName;
    @ApiModelProperty(value = "预计到货时间")
    private String expectArrivalTime;
    @ApiModelProperty(value = "供应商编号")
    private String supplierId;
    @ApiModelProperty(value = "供应商名称")
    private String supplierName;
    @ApiModelProperty(value = "采购订单号")
    private String orderId;
    @ApiModelProperty(value = "物流单号")
    private String logisticsId;
    @ApiModelProperty(value = "状态")
    private String statusDesc;
}
