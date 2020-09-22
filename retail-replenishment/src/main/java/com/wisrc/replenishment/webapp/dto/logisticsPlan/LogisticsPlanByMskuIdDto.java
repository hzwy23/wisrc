package com.wisrc.replenishment.webapp.dto.logisticsPlan;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LogisticsPlanByMskuIdDto {
    @ApiModelProperty(value = "物流计划ID", required = true)
    private String logisticsPlanId;

    @ApiModelProperty(value = "销售开始日期", required = true)
    private String salesStartTime;

    @ApiModelProperty(value = "销售截止日期", required = true)
    private String salesEndTime;

    @ApiModelProperty(value = "销售需求量", required = true)
    private Integer salesDemandQuantity;

    @ApiModelProperty(value = "物流运输周期", required = true)
    private Integer inspectionTrafficDay;

    @ApiModelProperty(value = "计划发货日期", required = true)
    private String deliveryPlanDate;

    @ApiModelProperty(value = "计划发货量", required = true)
    private Integer deliveryPlanQuantity;
}
