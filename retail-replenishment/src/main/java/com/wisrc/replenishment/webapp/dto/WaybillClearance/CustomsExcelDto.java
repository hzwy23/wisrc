package com.wisrc.replenishment.webapp.dto.WaybillClearance;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "报关单")
public class CustomsExcelDto {
    @ApiModelProperty(value = "纳税人识别号")
    private String taxpayerIdentificationNumber;
    @ApiModelProperty(value = "境内发货人")
    private String shipper;
    @ApiModelProperty(value = "境外收货人")
    private String receiver;
    @ApiModelProperty(value = "生产销售单位")
    private String unit;
    @ApiModelProperty(value = "运输方式")
    private String transportType;
    @ApiModelProperty(value = "监管方式")
    private String tradeType;
    @ApiModelProperty(value = "征免性质")
    private String exemptingNature;
    @ApiModelProperty(value = "贸易国（地区）")
    private String tradeCountry;
    @ApiModelProperty(value = "运抵国（地区）")
    private String destinationCountry;
    @ApiModelProperty(value = "境内货源地")
    private String goodsSource;
    @ApiModelProperty(value = "成交方式")
    private String dealType;
    @ApiModelProperty(value = "合同协议号")
    private String customsDeclareId;
    @ApiModelProperty(value = "件数")
    private String declareKindCnt;
    @ApiModelProperty(value = "包装种类")
    private String packType;
    @ApiModelProperty(value = "毛重（公斤）")
    private Double grossWeightTotal;
    @ApiModelProperty(value = "净重（公斤）")
    private Double netWeightTotal;
    @ApiModelProperty(value = "报关商品")
    private List<ReplenishmentExcelDto> replenishmentExcel;
}
