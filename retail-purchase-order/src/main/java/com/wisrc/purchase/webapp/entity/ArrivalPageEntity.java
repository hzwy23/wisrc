package com.wisrc.purchase.webapp.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Date;

@Data
public class ArrivalPageEntity {
    @ApiModelProperty(value = "到货通知单号")
    private String arrivalId;
    @ApiModelProperty(value = "录单日期")
    private Date applyDate;
    @ApiModelProperty(value = "发起人")
    private String employeeId;
    @ApiModelProperty(value = "预计到货时间")
    private Date estimateArrivalDate;
    @ApiModelProperty(value = "供应商编号")
    private String supplierId;
    @ApiModelProperty(value = "采购订单号")
    private String purchaseOrderId;
    @ApiModelProperty(value = "物流单号")
    private String logisticsId;
    @ApiModelProperty(value = "状态")
    private String statusDesc;
}
