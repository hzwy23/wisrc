package com.wisrc.purchase.webapp.vo.purchasePlan;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class PurchaseSettingEditVo {
    @ApiModelProperty(value = "备货周期", required = true)
    private int stockCycle;
    @ApiModelProperty(value = "计算周期", required = true)
    private int calculateCycleCd;
    @ApiModelProperty(value = "计算周期(星期)", required = false)
    private Integer calculateCycleWeekCd;
    @ApiModelProperty(value = "计算周期时间", required = true)
    private String datetime;
    @ApiModelProperty(value = "采购预警天数", required = true)
    private int purchaseWarmDay;
}
