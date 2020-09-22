package com.wisrc.merchandise.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel(value = "销售计划页面列表请求")
public class MskuSalesPlanPageVo {
    @ApiModelProperty(value = "店铺id", required = false)
    private String shopId;

    @ApiModelProperty(value = "小组", required = false)
    private String groupId;

    @ApiModelProperty(value = "小组负责人", required = false)
    private String manager;

    @ApiModelProperty(value = "关键字", required = false)
    private String findKey;

    @ApiModelProperty(value = "当前页码", required = false)
    @NotNull(message = "页码不能为空")
    private Integer currentPage;

    @ApiModelProperty(value = "页面数据数量", required = false)
    @NotNull(message = "列表数量不能为空")
    private Integer pageSize;
}
