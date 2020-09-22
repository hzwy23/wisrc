package com.wisrc.warehouse.webapp.vo.smartReplenishment;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SmartReplenishmentWaringVO {
    @ApiModelProperty(value = "预警店铺")
    private String shopId;

    @ApiModelProperty(value = "预警等级")
    private Integer WarningLevel;

    @ApiModelProperty(value = "销售状态")
    private String salesStatusCd;

    @ApiModelProperty(value = "关键字（MSKU/库存SKU/产品名称）")
    private String keyWords;
}
