package com.wisrc.replenishment.webapp.dto.WaybillClearance;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ReplenishmentExcelDto {
    @ApiModelProperty(value = "项号")
    private Integer num;
    @ApiModelProperty(value = "商品编号")
    private String customsNumber;
    @ApiModelProperty(value = "商品名称、规格型号")
    private String declarationElements;
    @ApiModelProperty(value = "净重数量")
    private Double netWeight;
    @ApiModelProperty(value = "申报数量")
    private Integer customsCount;
    @ApiModelProperty(value = "申报单位")
    private String mskuUnitName;
    @ApiModelProperty(value = "最终目的国（地区）")
    private String destinationCountry;
    @ApiModelProperty(value = "单价")
    private Double declareUnitPrice;
    @ApiModelProperty(value = "总价")
    private Double declareSubtotal;
    @ApiModelProperty(value = "币制")
    private String moneyType;
    @ApiModelProperty(value = "征免")
    private String exemptionMode;
}
