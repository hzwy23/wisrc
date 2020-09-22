package com.wisrc.replenishment.webapp.vo.logisticsPlan;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.sql.Date;

@Data
@ApiModel(description = "获取销售需求量")
public class GetSalesDemandQuantityVo {
    @ApiModelProperty(value = "销售开始日期", required = true)
    @NotNull
    private Date salesStartTime;

    @ApiModelProperty(value = "销售截止日期", required = true)
    @NotNull
    private Date salesEndTime;
}
