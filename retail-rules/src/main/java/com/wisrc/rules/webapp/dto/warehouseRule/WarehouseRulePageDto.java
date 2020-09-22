package com.wisrc.rules.webapp.dto.warehouseRule;

import com.wisrc.rules.webapp.dto.PageDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class WarehouseRulePageDto extends PageDto {
    @ApiModelProperty(value = "发货仓规则页面数据")
    private List<WarehouseRuleDto> warehouseRules;
}
