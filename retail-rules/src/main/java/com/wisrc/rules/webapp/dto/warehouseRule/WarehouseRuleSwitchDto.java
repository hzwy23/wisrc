package com.wisrc.rules.webapp.dto.warehouseRule;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Date;


@Data
public class WarehouseRuleSwitchDto {
    @ApiModelProperty(value = "开始有效期", required = false)
    private Date startDate;

    @ApiModelProperty(value = "结束有效期", required = false)
    private Date endDate;
}
