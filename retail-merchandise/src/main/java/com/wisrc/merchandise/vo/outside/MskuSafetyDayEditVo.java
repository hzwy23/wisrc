package com.wisrc.merchandise.vo.outside;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class MskuSafetyDayEditVo {
    @ApiModelProperty(value = "商品id")
    private String id;
    @ApiModelProperty(value = "商品安全库存天数")
    private Integer safetyStockDays;
}
