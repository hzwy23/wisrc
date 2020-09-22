package com.wisrc.warehouse.webapp.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class GetTotalNumQuery {
    @ApiModelProperty(value = "库存Sku")
    private List<String> skuIds;

    @ApiModelProperty(value = "仓库Id")
    private String warehouseId;
}
