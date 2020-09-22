package com.wisrc.order.webapp.vo.saleNvoice;

import com.wisrc.order.webapp.vo.PageVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "发货单管理页面")
public class SaleNvoicePageVo extends PageVo {
    @ApiModelProperty(value = "发货单号", position = 0)
    private String invoiceNumber;

    @ApiModelProperty(value = "状态", position = 1)
    private Integer statusCd;

    @ApiModelProperty(value = "波次号", position = 2)
    private String wmsWaveNumber;

    @ApiModelProperty(value = "订单号", position = 3)
    private String orderId;

    @ApiModelProperty(value = "原订单号", position = 4)
    private String originalOrderId;

    @ApiModelProperty(value = "商品编号")
    private String mskuId;

    @ApiModelProperty(value = "商品名称")
    private String mskuName;
}
