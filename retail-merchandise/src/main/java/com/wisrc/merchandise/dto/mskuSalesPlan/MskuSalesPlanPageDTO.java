package com.wisrc.merchandise.dto.mskuSalesPlan;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class MskuSalesPlanPageDTO {
    @ApiModelProperty(value = "商品id")
    private String id;

    @ApiModelProperty(value = "商品编号")
    private String msku;

    @ApiModelProperty(value = "店铺名称")
    private String shopName;

    @ApiModelProperty(value = "小组")
    private String team;

    @ApiModelProperty(value = "负责人")
    private String manager;

    @ApiModelProperty(value = "asin")
    private String ASIN;

    @ApiModelProperty(value = "商品计划")
    private List<PlanPageDTO> plans;

    @ApiModelProperty(value = "上架时间")
    private String shelfTime;
}
