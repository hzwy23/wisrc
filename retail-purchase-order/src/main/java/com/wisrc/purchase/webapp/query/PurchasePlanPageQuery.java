package com.wisrc.purchase.webapp.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class PurchasePlanPageQuery {
    @ApiModelProperty(value = "最迟采购开始日期")
    private Date lastPurchaseStartDate;
    @ApiModelProperty(value = "最迟采购结束日期")
    private Date lastPurchaseEndDate;
    @ApiModelProperty(value = "库存SKU")
    private String skuId;
    @ApiModelProperty(value = "状态")
    private Integer statusCd;
    @ApiModelProperty(value = "计算日期开始")
    private Date calculateDateStart;
    @ApiModelProperty(value = "计算日期结束")
    private Date calculateDateEnd;
    @ApiModelProperty(value = "供应商编号")
    private String supplierId;
    @ApiModelProperty(value = "排序参数", required = false)
    private String sortKey;
    @ApiModelProperty(value = "排序（-1、倒序，1、正序）", required = false)
    private String sort;
}
