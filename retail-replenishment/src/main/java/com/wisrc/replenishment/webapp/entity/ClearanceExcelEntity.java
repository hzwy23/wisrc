package com.wisrc.replenishment.webapp.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ClearanceExcelEntity {
    @ApiModelProperty(value = "序号")
    private Integer num;
    @ApiModelProperty(value = "HS编码")
    private String customsNumber;
    @ApiModelProperty(value = "清关名称")
    private String clearanceName;
    @ApiModelProperty(value = "原产地")
    private String countryOfOrigin;
    @ApiModelProperty(value = "材质")
    private String textureOfMateria;
    @ApiModelProperty(value = "用途")
    private String purposeDesc;
    @ApiModelProperty(value = "补货数量")
    private Integer replenishmentQuantity;
    @ApiModelProperty(value = "单价")
    private Double clearanceUnitPrice;
    @ApiModelProperty(value = "小计")
    private Double clearanceSubtotal;
    @ApiModelProperty(value = "商品id")
    private String mskuId;
    @ApiModelProperty(value = "店铺Id")
    private String shopId;
    @ApiModelProperty(value = "skuId")
    private String skuId;
}
