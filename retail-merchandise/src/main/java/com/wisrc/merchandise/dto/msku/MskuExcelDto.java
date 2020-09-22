package com.wisrc.merchandise.dto.msku;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Map;

@Data
public class MskuExcelDto {
    @ApiModelProperty(value = "商品编号")
    private String msku;
    @ApiModelProperty(value = "商品名称")
    private String mskuName;
    @ApiModelProperty(value = "产品编号")
    private String storeSku;
    @ApiModelProperty(value = "产品名称")
    private String storeName;
    @ApiModelProperty(value = "fnsku")
    private String fnsku;
    @ApiModelProperty(value = "asin")
    private String asin;
    @ApiModelProperty(value = "配送方式")
    private String delivery;
    @ApiModelProperty(value = "店铺名称")
    private String shopName;
    @ApiModelProperty(value = "小组名称")
    private String teamName;
    @ApiModelProperty(value = "负责人名称")
    private String manager;
    @ApiModelProperty(value = "销售状态")
    private String salesStatus;
    @ApiModelProperty(value = "上架时间")
    private String shelfTime;
    @ApiModelProperty(value = "更新时间")
    private String updateTime;
    @ApiModelProperty(value = "佣金系数")
    private Double commission;
}
