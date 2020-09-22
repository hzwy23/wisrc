package com.wisrc.sales.webapp.vo.replenishmentEstimate.get;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class GetReplenishmentEstimateListVO {
    @NotNull
    @ApiModelProperty(value = "msku库存与时间")
    private List<GetReplenishmentEstimateVO> rRVOlist;

}
