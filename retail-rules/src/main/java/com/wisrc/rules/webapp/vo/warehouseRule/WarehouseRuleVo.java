package com.wisrc.rules.webapp.vo.warehouseRule;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@ApiModel
public class WarehouseRuleVo {
    @ApiModelProperty(value = "规则名称", required = false)
    @NotEmpty(message = "规则名称不能为空")
    private String ruleName;

    @ApiModelProperty(value = "发货仓库", required = false)
    @NotEmpty(message = "发货仓库不能为空")
    private String warehouseId;

    @ApiModelProperty(value = "优先级", required = false)
    @NotNull(message = "优先级不能为空")
    private Integer priorityNumber;

    @ApiModelProperty(value = "开始有效期", required = false)
    @NotNull(message = "有效期不能为空")
    private String startDate;

    @ApiModelProperty(value = "结束有效期", required = false)
    @NotNull(message = "有效期不能为空")
    private String endDate;

    @ApiModelProperty(value = "备注", required = false)
    private String remark;

    @ApiModelProperty(value = "指定店铺", required = false)
    private List<String> shopIds;

    @ApiModelProperty(value = "订单包含产品类目", required = false)
    private List<String> classifyCds;

    @ApiModelProperty(value = "订单包含库存SKU", required = false)
    private List<String> skuIds;
}
