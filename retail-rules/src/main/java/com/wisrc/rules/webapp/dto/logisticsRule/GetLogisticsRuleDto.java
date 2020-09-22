package com.wisrc.rules.webapp.dto.logisticsRule;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Date;
import java.util.List;

@Data
public class GetLogisticsRuleDto {
    @ApiModelProperty(value = "规则名称")
    private String ruleName;

    @ApiModelProperty(value = "发货物流渠道Id")
    private String offerId;

    @ApiModelProperty(value = "优先级")
    private Integer priorityNumber;

    @ApiModelProperty(value = "开始有效期")
    private Date startDate;

    @ApiModelProperty(value = "结束有效期")
    private Date endDate;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "订单最小金额")
    private Double minTotalAmount;

    @ApiModelProperty(value = "订单最大金额")
    private Double maxTotalAmount;

    @ApiModelProperty(value = "币种金额")
    private String totalAmountCurrency;

    @ApiModelProperty(value = "订单最小重量")
    private Double minWeight;

    @ApiModelProperty(value = "订单最大重量")
    private Double maxWeight;

    @ApiModelProperty(value = "产品分类")
    private List classifyIds;

    @ApiModelProperty(value = "指定仓库")
    private List warehouseIds;

    @ApiModelProperty(value = "指定店铺")
    private List shopIds;

    @ApiModelProperty(value = "指定国家")
    private List countryIds;

    @ApiModelProperty(value = "指定邮政编码")
    private List zipCode;

    @ApiModelProperty(value = "买家自选物流渠道")
    private List offerIds;

    @ApiModelProperty(value = "包含特性标签")
    private List labelIds;

    @ApiModelProperty(value = "指定库存SKU")
    private List skuIds;
}
