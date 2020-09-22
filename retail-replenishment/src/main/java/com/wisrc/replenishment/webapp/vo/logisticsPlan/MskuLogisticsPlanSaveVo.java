package com.wisrc.replenishment.webapp.vo.logisticsPlan;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@ApiModel(description = "商品物流计划保存")
public class MskuLogisticsPlanSaveVo extends MskuLogisticsPlanVo {
    @ApiModelProperty(value = "批量商品保存", required = true)
    @NotEmpty
    private List<LogisticsPlanSaveVo> planBatch;
}
