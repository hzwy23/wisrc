package com.wisrc.merchandise.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "商品信息关联主数据")
public class MskuInfoPageOutsideVo {
    @ApiModelProperty(value = "店铺id", required = false)
    private String shopId;

    @ApiModelProperty(value = "商品编号", required = false)
    private String mskuId;

    @ApiModelProperty(value = "负责人", required = false)
    private String manager;

    @ApiModelProperty(value = "销售状态", required = false)
    private Integer salesStatus;

    @ApiModelProperty(value = "关键字（ASIN/MSKU/库存SKU/品名）", required = false)
    private String findKey;

    @ApiModelProperty(value = "页码", required = false)
    private Integer pageNum;

    @ApiModelProperty(value = "单页数量", required = false)
    private Integer pageSize;

    @ApiModelProperty(value = "销售状态(只获取需求规定的销售状态)", required = false)
    private List salesStatusList;

    @ApiModelProperty(value = "是否获取已停用数据,是则传1", required = false)
    private Integer doesDelete;
}
