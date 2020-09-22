package com.wisrc.replenishment.webapp.dto.WaybillClearance;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ClearanceExcelDto {
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
    @ApiModelProperty(value = "数量")
    private String replenishmentQuantity;
    @ApiModelProperty(value = "单价")
    private String clearanceUnitPrice;
    @ApiModelProperty(value = "小计")
    private String clearanceSubtotal;
}
