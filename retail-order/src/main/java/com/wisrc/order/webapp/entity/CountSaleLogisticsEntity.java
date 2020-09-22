package com.wisrc.order.webapp.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CountSaleLogisticsEntity {
    @ApiModelProperty(value = "物流商报价ID（只取小包报价物流商）")
    private String offerId;
    @ApiModelProperty(value = "运单号(实际是物流单号)")
    private String logisticsId;
    @ApiModelProperty(value = "数量")
    private Integer count;
}
