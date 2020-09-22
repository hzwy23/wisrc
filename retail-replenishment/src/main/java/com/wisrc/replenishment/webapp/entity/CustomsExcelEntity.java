package com.wisrc.replenishment.webapp.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Date;

@Data
public class CustomsExcelEntity {
    @ApiModelProperty(value = "海关编号")
    private String customsDeclareId;
    @ApiModelProperty(value = "运输方式")
    private Integer transportTypeCd;
    @ApiModelProperty(value = "监管方式")
    private Integer tradeTypeCd;
    @ApiModelProperty(value = "征免性质")
    private Integer exemptingNatureCd;
    @ApiModelProperty(value = "贸易国（地区）")
    private String tradeCountry;
    @ApiModelProperty(value = "运抵国（地区）")
    private String destinationCountry;
    @ApiModelProperty(value = "运输至")
    private String destinationAddress;
    @ApiModelProperty(value = "境内货源地")
    private String goodsSource;
    @ApiModelProperty(value = "成交方式")
    private Integer dealTypeCd;
    @ApiModelProperty(value = "合同协议号")
    private String customsDeclareNumber;
    @ApiModelProperty(value = "件数")
    private Integer declareKindCnt;
    @ApiModelProperty(value = "包装种类")
    private Integer packTypeCd;
    @ApiModelProperty(value = "外汇币制")
    private Integer moneyTypeCd;
    @ApiModelProperty(value = "征免方式")
    private Integer exemptionModeCd;
    @ApiModelProperty(value = "报关日期")
    private Date customsDeclarationDate;
    @ApiModelProperty(value = "公司名称")
    private String companyName;
    @ApiModelProperty(value = "公司地址")
    private String companyAddress;
    @ApiModelProperty(value = "合同签约地点")
    private String signAddress;
}
