package com.wisrc.purchase.webapp.dto.purchasePlan;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class WeekAttrSelectorDto {
    @ApiModelProperty(value = "每周几对应编号")
    private Integer calculateCycleWeekCd;
    @ApiModelProperty(value = "每周几对应描述")
    private String calculateCycleWeekDesc;
}
