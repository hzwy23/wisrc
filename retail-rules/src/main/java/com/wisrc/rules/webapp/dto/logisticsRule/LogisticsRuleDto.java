package com.wisrc.rules.webapp.dto.logisticsRule;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LogisticsRuleDto {
    @ApiModelProperty(value = "规则名称")
    private String ruleId;

    @ApiModelProperty(value = "规则名称")
    private String ruleName;

    @ApiModelProperty(value = "优先级")
    private Integer priorityNumber;

    @ApiModelProperty(value = "状态")
    private Integer statusCd;

    @ApiModelProperty(value = "发货物流渠道")
    private String offerChannel;

    @ApiModelProperty(value = "最后更新人")
    private String modifyUser;

    @ApiModelProperty(value = "最后更新时间")
    private String modifyTime;
}
