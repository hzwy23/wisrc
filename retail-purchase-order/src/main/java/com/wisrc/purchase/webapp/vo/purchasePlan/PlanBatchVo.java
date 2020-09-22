package com.wisrc.purchase.webapp.vo.purchasePlan;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@ApiModel
public class PlanBatchVo {
    @ApiModelProperty(value = "计划编号")
    @NotEmpty(message = "请选择至少一项")
    private List<String> uuids;
}
