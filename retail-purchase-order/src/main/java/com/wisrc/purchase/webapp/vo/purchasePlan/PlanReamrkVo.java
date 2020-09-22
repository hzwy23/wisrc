package com.wisrc.purchase.webapp.vo.purchasePlan;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PlanReamrkVo {
    @ApiModelProperty(value = "备注", required = true)
    private String remark;
}
