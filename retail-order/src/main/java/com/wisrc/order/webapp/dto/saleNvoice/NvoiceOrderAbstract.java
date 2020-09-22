package com.wisrc.order.webapp.dto.saleNvoice;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public abstract class NvoiceOrderAbstract {
    @ApiModelProperty(value = "发货单号", position = 0)
    private String invoiceNumber;

    @ApiModelProperty(value = "状态", position = 1)
    private String statusDesc;

    @ApiModelProperty(value = "波次号", position = 2)
    private String wmsWaveNumber;

    @ApiModelProperty(value = "订单号", position = 3)
    private String orderId;

    @ApiModelProperty(value = "原始订单号", position = 4)
    private String originalOrderId;

    @ApiModelProperty(value = "发货物流渠道", position = 7)
    private String channelName;

    @ApiModelProperty(value = "物流单号", position = 8)
    private String logisticsId;

    @ApiModelProperty(value = "物流费用", position = 9)
    private String freight;
}
