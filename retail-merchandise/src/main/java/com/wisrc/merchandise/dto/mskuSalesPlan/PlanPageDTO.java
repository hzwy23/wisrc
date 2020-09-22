package com.wisrc.merchandise.dto.mskuSalesPlan;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Map;

@Data
public class PlanPageDTO {
    @ApiModelProperty(value = "销售状态")
    private Map salesStatus;

    @ApiModelProperty(value = "计划开始日期")
    private String startDate;

    @ApiModelProperty(value = "计划结束日期")
    private String expiryDate;

    @ApiModelProperty(value = "预计日销量")
    private Integer exptDailySales;

    @ApiModelProperty(value = "标准指导价")
    private Double guidePrice;

    @ApiModelProperty(value = "销售状态")
    private String planId;
}
