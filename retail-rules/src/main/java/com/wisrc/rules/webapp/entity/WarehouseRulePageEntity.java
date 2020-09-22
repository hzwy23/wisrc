package com.wisrc.rules.webapp.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class WarehouseRulePageEntity {
    @ApiModelProperty(value = "规则编号")
    private String ruleId;

    @ApiModelProperty(value = "规则名称")
    private String ruleName;

    @ApiModelProperty(value = "优先级")
    private Integer priorityNumber;

    @ApiModelProperty(value = "状态")
    private String statusDesc;

    @ApiModelProperty(value = "发货仓库")
    private String warehouseId;

    @ApiModelProperty(value = "最后更新人")
    private String modifyUser;

    @ApiModelProperty(value = "最后更新时间")
    private String modifyTime;
}
