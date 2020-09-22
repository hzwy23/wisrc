package com.wisrc.replenishment.webapp.vo.logisticsPlan;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "物流计划编辑")
public class LogisticsPlanEditVo extends LogisticsPlanVo {
    @ApiModelProperty(value = "物流计划ID", required = true)
    private String logisticsPlanId;
}
