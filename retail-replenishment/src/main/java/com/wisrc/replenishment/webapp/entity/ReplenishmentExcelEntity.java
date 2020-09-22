package com.wisrc.replenishment.webapp.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ReplenishmentExcelEntity {
    @ApiModelProperty(value = "商品编号")
    private String customsNumber;
    @ApiModelProperty(value = "商品名称、规格型号")
    private String declarationElements;
    @ApiModelProperty(value = "毛重数量")
    private Double grossWeight;
    @ApiModelProperty(value = "净重数量")
    private Double netWeight;
    @ApiModelProperty(value = "装箱箱数")
    private Integer numberOfBoxes;
    @ApiModelProperty(value = "重量(kg/箱)")
    private Double packingWeight;
    @ApiModelProperty(value = "装量(PCS/箱)")
    private Integer packingQuantity;
    @ApiModelProperty(value = "申报单位")
    private String mskuUnitName;
    @ApiModelProperty(value = "单价")
    private Double declareUnitPrice;
    @ApiModelProperty(value = "总价")
    private Double declareSubtotal;
}
