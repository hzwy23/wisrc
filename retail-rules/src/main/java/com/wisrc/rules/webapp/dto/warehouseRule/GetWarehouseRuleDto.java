package com.wisrc.rules.webapp.dto.warehouseRule;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@ApiModel
public class GetWarehouseRuleDto {
    @ApiModelProperty(value = "规则名称")
    private String ruleName;

    @ApiModelProperty(value = "默认发货仓库")
    private Map offerChannel;

    @ApiModelProperty(value = "优先级")
    private Integer priorityNumber;

    @ApiModelProperty(value = "开始有效期", required = false)
    private String startDate;

    @ApiModelProperty(value = "结束有效期", required = false)
    private String endDate;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "指定店铺")
    private List<String> shopIds;

    @ApiModelProperty(value = "订单包含产品类目")
    private List<String> classifyCds;

    @ApiModelProperty(value = "订单包含库存SKU")
    private List<String> skuIds;
}
