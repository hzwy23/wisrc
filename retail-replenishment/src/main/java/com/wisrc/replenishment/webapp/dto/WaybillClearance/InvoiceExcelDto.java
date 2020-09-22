package com.wisrc.replenishment.webapp.dto.WaybillClearance;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "发票")
public class InvoiceExcelDto {
    @ApiModelProperty(value = "买方")
    private String buyer;
    @ApiModelProperty(value = "报关编号")
    private String customsDeclareId;
    @ApiModelProperty(value = "运输至")
    private String destinationAddress;
    @ApiModelProperty(value = "报关日期")
    private String customsDeclarationDate;
    @ApiModelProperty(value = "合计美元")
    private double declareAllSubtotal;
    @ApiModelProperty(value = "外汇币制")
    private String moneyType;
    @ApiModelProperty(value = "报关商品")
    private List<ReplenishInvoiceExcelDto> replenishmentExcel;
}
