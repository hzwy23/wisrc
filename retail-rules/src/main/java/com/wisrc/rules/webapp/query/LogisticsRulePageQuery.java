package com.wisrc.rules.webapp.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LogisticsRulePageQuery {
    @ApiModelProperty(value = "规则名称")
    private String ruleName;

    @ApiModelProperty(value = "发货物流渠道")
    private String offerId;

    @ApiModelProperty(value = "最后更新开始时间")
    private String modifyStartTime;

    @ApiModelProperty(value = "最后更新结束时间")
    private String modifyEndTime;
}
