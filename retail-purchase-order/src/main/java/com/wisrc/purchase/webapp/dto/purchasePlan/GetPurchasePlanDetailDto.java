package com.wisrc.purchase.webapp.dto.purchasePlan;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class GetPurchasePlanDetailDto {
    @ApiModelProperty(value = "日期")
    private Date planDate;
    @ApiModelProperty(value = "预计销量")
    private Integer expectSales;
    @ApiModelProperty(value = "日均销量")
    private Integer avgSales;
    @ApiModelProperty(value = "可用库存(在仓+在途+待采购)")
    private Integer availableStock;
    @ApiModelProperty(value = "分配后结余")
    private Integer assignBalance;
    @ApiModelProperty(value = "最低库存量")
    private Integer minStock;
    @ApiModelProperty(value = "最少起订量")
    private Integer minimum;
    @ApiModelProperty(value = "建议采购数")
    private Integer suggestPurchase;
    @ApiModelProperty(value = "最迟采购日期")
    private Date lastPurchaseDate;
}
