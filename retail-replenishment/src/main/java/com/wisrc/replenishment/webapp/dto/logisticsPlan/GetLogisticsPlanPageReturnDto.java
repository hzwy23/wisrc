package com.wisrc.replenishment.webapp.dto.logisticsPlan;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Date;

@Data
public class GetLogisticsPlanPageReturnDto {
    @ApiModelProperty(value = "商品编号")
    private String id;

    @ApiModelProperty(value = "商品sku")
    private String mskuId;

    @ApiModelProperty(value = "店铺名称")
    private String shopName;

    @ApiModelProperty(value = "产品编号")
    private String skuId;

    @ApiModelProperty(value = "产品名称")
    private String productName;

    @ApiModelProperty(value = "负责人")
    private String employee;

    @ApiModelProperty(value = "销售状态")
    private String salesStatus;

    @ApiModelProperty(value = "asin")
    private String asin;

    @ApiModelProperty(value = "fnsku")
    private String fnsku;

    @ApiModelProperty(value = "计划截止日期")
    private Date salesEndTime;

    @ApiModelProperty(value = "计划发货量")
    private Integer deliveryPlanQuantity;
}
