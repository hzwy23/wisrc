package com.wisrc.replenishment.webapp.query.logisticBill;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class HistoryQuery {
    @ApiModelProperty(value = "商品id")
    private String mskuId;
    @ApiModelProperty(value = "店铺id")
    private String shopId;
}
