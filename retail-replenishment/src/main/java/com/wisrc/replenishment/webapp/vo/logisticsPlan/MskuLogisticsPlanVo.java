package com.wisrc.replenishment.webapp.vo.logisticsPlan;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MskuLogisticsPlanVo {
    @ApiModelProperty(value = "批量商品保存", required = true)
    private Object planBatch;
}
