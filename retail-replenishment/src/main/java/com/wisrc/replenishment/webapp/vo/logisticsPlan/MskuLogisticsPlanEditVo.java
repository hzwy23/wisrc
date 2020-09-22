package com.wisrc.replenishment.webapp.vo.logisticsPlan;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@ApiModel(description = "商品物流计划保存")
public class MskuLogisticsPlanEditVo {
    @ApiModelProperty(value = "物流运输周期", required = true)
    private Integer inspectionTrafficDay;

    @ApiModelProperty(value = "计划发货日期", required = true)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date deliveryPlanDate;

    @ApiModelProperty(value = "计划发货量", required = true)
    private Integer deliveryPlanQuantity;
}
