package com.wisrc.rules.webapp.vo.warehouseRule;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class WarehouseRuleSwitchVo {
    @ApiModelProperty(value = "开始有效期", required = false)
    private String startDate;

    @ApiModelProperty(value = "结束有效期", required = false)
    private String endDate;
}
