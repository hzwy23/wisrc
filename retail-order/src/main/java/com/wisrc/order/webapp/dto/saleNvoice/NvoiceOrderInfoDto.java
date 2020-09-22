package com.wisrc.order.webapp.dto.saleNvoice;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(description = "发货单详情")
public class NvoiceOrderInfoDto extends NvoiceOrderAbstract {
    @ApiModelProperty(value = "商品信息")
    private List<OrderCommodityDto> orderCommodities;

    @ApiModelProperty(value = "商品种类数")
    private Integer commodityKind;

    @ApiModelProperty(value = "商品总件数")
    private Integer commodityNum;
}
