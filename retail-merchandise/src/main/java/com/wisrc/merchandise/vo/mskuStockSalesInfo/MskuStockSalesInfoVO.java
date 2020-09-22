package com.wisrc.merchandise.vo.mskuStockSalesInfo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class MskuStockSalesInfoVO {
    @NotNull(message = "店铺id为必填参数")
    @ApiModelProperty(value = "店铺id", required = true)
    private String shopId;

    @NotNull(message = "msku为必填参数")
    @ApiModelProperty(value = "msku", required = true)
    private String mskuId;

    @ApiModelProperty(value = "fba在仓库存数量")
    private Integer fbaOnWarehouseStockNum;

    @ApiModelProperty(value = "fba在途库存数量")
    private Integer fbaOnWayStockNum;

    @ApiModelProperty(value = "昨日销量")
    private Integer yesterdaySalesNum;

    @ApiModelProperty(value = "前日销量")
    private Integer dayBeforeYesterdaySalesNum;

    @ApiModelProperty(value = "上前销量")
    private Integer previousSalesNum;
}
