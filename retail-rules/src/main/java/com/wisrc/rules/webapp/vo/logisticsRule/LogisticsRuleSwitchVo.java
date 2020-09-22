package com.wisrc.rules.webapp.vo.logisticsRule;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LogisticsRuleSwitchVo {
    @ApiModelProperty(value = "有效期开始时间", required = false)
    private String startDate;

    @ApiModelProperty(value = "有效期结束时间", required = false)
    private String endDate;
}
