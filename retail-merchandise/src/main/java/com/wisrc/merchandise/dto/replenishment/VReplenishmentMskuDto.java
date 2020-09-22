package com.wisrc.merchandise.dto.replenishment;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class VReplenishmentMskuDto {
    @ApiModelProperty(value = "商品唯一ID号")
    private String commodityId;
    @ApiModelProperty(value = "FBA发货数量")
    private String deliveryNumberTotal;
    @ApiModelProperty(value = "FBA收货数量")
    private String signInQuantityTotal;
    @ApiModelProperty(value = "FBA在途数量")
    private String underWayQuantity;
}
