package com.wisrc.merchandise.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class GetMskuFBAVo {
    @ApiModelProperty(value = "店铺编号", required = false)
    private String shopId;
    @ApiModelProperty(value = "销售状态", required = false)
    private Integer salesStatusCd;
    @ApiModelProperty(value = "配送方式（1、FBA，2、FBM）", required = false)
    private Integer deliveryMode;
    @ApiModelProperty(value = "关键字(Msku/产品编号/品名)", required = false)
    private String findKey;
}
