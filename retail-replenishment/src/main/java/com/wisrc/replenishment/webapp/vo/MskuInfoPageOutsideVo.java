package com.wisrc.replenishment.webapp.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "商品信息关联主数据")
public class MskuInfoPageOutsideVo extends PageVo {
    @ApiModelProperty(value = "店铺id", required = false)
    private String shopId;

    @ApiModelProperty(value = "负责人", required = false)
    private String manager;

    @ApiModelProperty(value = "销售状态", required = false)
    private Integer salesStatus;

    @ApiModelProperty(value = "关键字", required = false)
    private String findKey;
    @ApiModelProperty(value = "是否获取已删除数据", required = false)
    private Integer doesDelete;
}
