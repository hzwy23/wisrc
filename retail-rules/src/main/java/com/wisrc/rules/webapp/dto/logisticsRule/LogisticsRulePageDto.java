package com.wisrc.rules.webapp.dto.logisticsRule;

import com.wisrc.rules.webapp.dto.PageDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class LogisticsRulePageDto extends PageDto {
    @ApiModelProperty(value = "物流规则页面数据")
    private List<LogisticsRuleDto> logisticsRules;
}
