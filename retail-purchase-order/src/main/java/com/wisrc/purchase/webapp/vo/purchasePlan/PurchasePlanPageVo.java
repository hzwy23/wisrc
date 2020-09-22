package com.wisrc.purchase.webapp.vo.purchasePlan;

import com.wisrc.purchase.webapp.vo.PageVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class PurchasePlanPageVo extends PageVo {
    @ApiModelProperty(value = "最迟采购开始日期", required = false)
    private String lastPurchaseStartDate;
    @ApiModelProperty(value = "最迟采购结束日期", required = false)
    private String lastPurchaseEndDate;
    @ApiModelProperty(value = "库存SKU", required = false)
    private String skuId;
    @ApiModelProperty(value = "状态", required = false)
    private Integer statusCd;
    @ApiModelProperty(value = "计算日期开始", required = false)
    private String calculateDateStart;
    @ApiModelProperty(value = "计算日期结束", required = false)
    private String calculateDateEnd;
    @ApiModelProperty(value = "供应商编号", required = false)
    private String supplierId;
    @ApiModelProperty(value = "排序参数(即返回参数key值)", required = false)
    private String sortKey;
    @ApiModelProperty(value = "排序（-1、倒序，1、正序）", required = false)
    private Integer sort;
}
