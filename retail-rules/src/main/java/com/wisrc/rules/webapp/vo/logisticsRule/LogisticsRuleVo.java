package com.wisrc.rules.webapp.vo.logisticsRule;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
@ApiModel(description = "物流规则保存")
public class LogisticsRuleVo {
    @ApiModelProperty(value = "规则名称")
    @NotEmpty(message = "规则名称不能为空")
    private String ruleName;

    @ApiModelProperty(value = "物流报价ID")
    @NotEmpty(message = "物流报价ID不能为空")
    private String offerId;

    @ApiModelProperty(value = "优先级ID")
    @NotNull(message = "优先级ID不能为空")
    private Integer priorityNumber;

    @ApiModelProperty(value = "有效期开始时间")
    @NotNull(message = "有效期开始时间不能为空")
    private Date startDate;

    @ApiModelProperty(value = "有效期结束时间")
    @NotNull(message = "有效期结束时间不能为空")
    private Date endDate;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "最小金额")
    private Double minTotalAmount;

    @ApiModelProperty(value = "最大金额")
    private Double maxTotalAmount;

    @ApiModelProperty(value = "币种金额")
    private String totalAmountCurrency;

    @ApiModelProperty(value = "最小重量(kg)")
    private Double minWeight;

    @ApiModelProperty(value = "最大重量(kg)")
    private Double maxWeight;

    @ApiModelProperty(value = "邮编")
    private List<String> zipCodes;

    @ApiModelProperty(value = "国家编码")
    private List<String> countryCds;

    @ApiModelProperty(value = "店铺编码")
    private List<String> shopIds;

    @ApiModelProperty(value = "仓库编码")
    private List<String> warehouseIds;

    @ApiModelProperty(value = "产品分类")
    private List<String> classifyCds;

    @ApiModelProperty(value = "产品标签")
    private List<Integer> labelCds;

    @ApiModelProperty(value = "产品标签")
    private List<String> skuIds;

    @ApiModelProperty(value = "买家自选物流渠道")
    private List<String> offerIds;
}
