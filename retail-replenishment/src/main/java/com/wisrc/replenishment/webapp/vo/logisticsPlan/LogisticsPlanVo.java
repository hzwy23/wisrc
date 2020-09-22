package com.wisrc.replenishment.webapp.vo.logisticsPlan;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Date;

@Data
@ApiModel(description = "物流计划")
public class LogisticsPlanVo {
    @ApiModelProperty(value = "销售开始日期", required = true)
    private Date salesStartTime;

    @ApiModelProperty(value = "销售截止日期", required = true)
    private Date salesEndTime;

    @ApiModelProperty(value = "销售需求量", required = true)
    private Integer salesDemandQuantity;

    @ApiModelProperty(value = "物流运输周期", required = true)
    private Integer inspectionTrafficDay;

    @ApiModelProperty(value = "计划发货日期", required = true)
    private Date deliveryPlanDate;

    @ApiModelProperty(value = "计划发货量", required = true)
    private Integer deliveryPlanQuantity;
}
