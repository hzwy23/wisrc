package com.wisrc.replenishment.webapp.dto.WaybillClearance;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class PackingExcelDto {
    @ApiModelProperty(value = "客户")
    private String customer;
    @ApiModelProperty(value = "报关日期")
    private String customsDeclarationDate;
    @ApiModelProperty(value = "报关编号")
    private String customsDeclareId;
    @ApiModelProperty(value = "运抵国")
    private String destinationCountry;
    @ApiModelProperty(value = "运输至")
    private String destinationAddress;
    @ApiModelProperty(value = "合计箱数")
    private Integer numberOfBoxesTotal;
    @ApiModelProperty(value = "合计数量")
    private Integer customsCountTotal;
    @ApiModelProperty(value = "合计毛重")
    private Double grossWeightTotal;
    @ApiModelProperty(value = "合计净重")
    private Double netWeightTotal;
    @ApiModelProperty(value = "报关商品")
    private List<ReplenishPackingExcelDto> replenishmentExcel;
}
