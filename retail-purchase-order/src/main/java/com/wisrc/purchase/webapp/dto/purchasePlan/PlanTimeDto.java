package com.wisrc.purchase.webapp.dto.purchasePlan;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class PlanTimeDto {
    @ApiModelProperty(value = "截至销售日")
    private Date endSalesDate;
    @ApiModelProperty(value = "备货周期")
    private Integer stockCycle;
    @ApiModelProperty(value = "国际运输时间")
    private Integer internationalTransportDays;
    @ApiModelProperty(value = "安全库存天数")
    private Integer safetyStockDays;
}
