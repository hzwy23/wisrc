package com.wisrc.rules.webapp.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class WarehouseRuleDefineQuery {
    @ApiModelProperty(value = "规则名称", required = false)
    private String ruleName;

    @ApiModelProperty(value = "发货仓库", required = false)
    private String warehouseId;

    @ApiModelProperty(value = "最后更新开始时间", required = false)
    private String modifyStartTime;

    @ApiModelProperty(value = "最后更新结束时间", required = false)
    private String modifyEndTime;
}
