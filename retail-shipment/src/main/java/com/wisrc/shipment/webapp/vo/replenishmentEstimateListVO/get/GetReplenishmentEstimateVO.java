package com.wisrc.shipment.webapp.vo.replenishmentEstimateListVO.get;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class GetReplenishmentEstimateVO {

    @NotBlank
    @ApiModelProperty(value = "id", required = true)
    private String id;

    @NotBlank
    @ApiModelProperty(value = "店铺id", required = true)
    private String shopId;

    @NotBlank
    @ApiModelProperty(value = "mskuId", required = true)
    private String mskuId;

    @NotNull
    @ApiModelProperty(value = "开始时间")
    private String startTime;

    @ApiModelProperty(value = "结束时间")
    private String endTime;

    @NotNull
    @Range(min = 0)
    @ApiModelProperty(value = "FBA在仓库存", required = true)
    private Integer fbaOnWarehouseStockNum;

    @NotNull
    @Range(min = 0)
    @ApiModelProperty(value = "FBA在途库存", required = true)
    private Integer fbaOnWayStockNum;
}
