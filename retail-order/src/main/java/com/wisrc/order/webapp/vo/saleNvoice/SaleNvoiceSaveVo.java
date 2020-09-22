package com.wisrc.order.webapp.vo.saleNvoice;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@ApiModel(value = "新建发货单")
public class SaleNvoiceSaveVo {
    @ApiModelProperty(value = "发货单状态")
    @NotNull
    private Integer statusCd;

    @ApiModelProperty(value = "总重量")
    @NotEmpty
    private String totalWeight;

    @ApiModelProperty(value = "订单号")
    @NotEmpty
    private String orderId;
}
