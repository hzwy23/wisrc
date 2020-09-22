package com.wisrc.merchandise.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@ApiModel(description = "商品修改数据")
public class MskuInfoVo {
    @ApiModelProperty(value = "小组", required = true)
    @NotEmpty
    private String groupId;

    @ApiModelProperty(value = "小组负责人", required = true)
    @NotEmpty
    private String manager;

    @ApiModelProperty(value = "销售状态", required = true)
    @NotNull
    private Integer salesStatus;

    @ApiModelProperty(value = "父Asin", required = false)
    private String parentAsin;

    @ApiModelProperty(value = "商品名称", required = true)
    @NotEmpty
    private String mskuName;

    @ApiModelProperty(value = "产品id", required = true)
    @NotEmpty
    private String storeSkuId;

    @ApiModelProperty(value = "asin", required = true)
    @NotEmpty
    private String asin;

    @ApiModelProperty(value = "fnsku", required = true)
    private String fnsku;

    @ApiModelProperty(value = "亚马逊佣金", required = true)
    @NotNull
    private Double commission;


}
