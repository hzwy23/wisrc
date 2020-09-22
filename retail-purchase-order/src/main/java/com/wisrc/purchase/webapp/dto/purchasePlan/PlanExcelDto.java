package com.wisrc.purchase.webapp.dto.purchasePlan;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class PlanExcelDto {
    @ApiModelProperty(value = "库存SKU")
    private String skuId;
    @ApiModelProperty(value = "产品中文名")
    private String productName;
    @ApiModelProperty(value = "计算日期")
    private Date calculateDate;
    @ApiModelProperty(value = "截至销售日")
    private Date endSalesDate;
    @ApiModelProperty(value = "建议采购数")
    private Integer recommendPurchase;
    @ApiModelProperty(value = "最迟采购日期")
    private Date lastPurchaseDate;
    @ApiModelProperty(value = "开始缺货日")
    private Date startOutStock;
    @ApiModelProperty(value = "预计到仓日")
    private Date expectInWarehouse;
    @ApiModelProperty(value = "可用库存")
    private Integer availableStock;
    @ApiModelProperty(value = "预计销量")
    private Integer sumSales;
    @ApiModelProperty(value = "日均销量")
    private Integer avgSales;
    @ApiModelProperty(value = "最低库存量")
    private Integer minStock;
    @ApiModelProperty(value = "通用交期")
    private Integer generalDelivery;
    @ApiModelProperty(value = "国内运输时间")
    private Integer haulageDays;
    @ApiModelProperty(value = "国际运输时间")
    private Integer internationalTransportDays;
    @ApiModelProperty(value = "安全库存天数")
    private Integer safetyStockDays;
    @ApiModelProperty(value = "最少起订量")
    private Integer minimum;
    @ApiModelProperty(value = "供应商编号")
    private String supplierId;
    @ApiModelProperty(value = "状态")
    private Integer statusCd;
    @ApiModelProperty(value = "采购计划单号")
    private String purchaseId;
    @ApiModelProperty(value = "采购订单号")
    private String orderId;
}
