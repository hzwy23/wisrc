package com.wisrc.purchase.webapp.vo.purchasePlan;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class PlanTimeEditVo {
    @ApiModelProperty(value = "备货周期", required = true)
    private Integer stockCycle;
    @ApiModelProperty(value = "国际运输时间", required = true)
    private Integer internationalTransportDays;
    @ApiModelProperty(value = "安全库存天数", required = true)
    private Integer safetyStockDays;
}
