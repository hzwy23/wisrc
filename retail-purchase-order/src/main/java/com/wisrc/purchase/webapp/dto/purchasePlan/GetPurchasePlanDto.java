package com.wisrc.purchase.webapp.dto.purchasePlan;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class GetPurchasePlanDto {
    @ApiModelProperty(value = "库存SKU")
    private String skuId;
    @ApiModelProperty(value = "产品中文名")
    private String productName;
    @ApiModelProperty(value = "计算日期")
    private Date calculateDate;
    @ApiModelProperty(value = "计划天数")
    private Integer planDay;
    @ApiModelProperty(value = "备货周期")
    private Integer stockCycle;
    @ApiModelProperty(value = "通用交期")
    private Integer generalDelivery;
    @ApiModelProperty(value = "安全库存天数")
    private Integer safetyStockDays;
    @ApiModelProperty(value = "运输时间-国内")
    private Integer haulageDays;
    @ApiModelProperty(value = "运输时间-国际")
    private Integer internationalTransportDays;
    @ApiModelProperty(value = "合计销量")
    private Integer sumSales;
    @ApiModelProperty(value = "建议数量")
    private Integer suggestCount;
    @ApiModelProperty(value = "建议日期")
    private Date suggestDate;
    @ApiModelProperty(value = "计算类型")
    private String calculateTypeDesc;
    @ApiModelProperty(value = "计算时间")
    private Date calculateTime;
    @ApiModelProperty(value = "更改人")
    private String modifyUser;
    @ApiModelProperty(value = "详情")
    private List<GetPurchasePlanDetailDto> purchasePlanDetails;
}
