package com.wisrc.replenishment.webapp.dto.WaybillClearance;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ContractExcelDto {
    @ApiModelProperty(value = "卖方")
    private String name;
    @ApiModelProperty(value = "卖方地址")
    private String address;
    @ApiModelProperty(value = "电话")
    private String companyTelephone;
    @ApiModelProperty(value = "传  真")
    private String companyFax;
    @ApiModelProperty(value = "合同号")
    private String customsDeclareId;
    @ApiModelProperty(value = "买方")
    private String companyName;
    @ApiModelProperty(value = "日期")
    private String date;
    @ApiModelProperty(value = "买方地址")
    private String companyAddress;
    @ApiModelProperty(value = "签约地址")
    private String signAddress;
    @ApiModelProperty(value = "总值")
    private double total;
    @ApiModelProperty(value = "总值中文")
    private String totalCN;
    @ApiModelProperty(value = "包装种类")
    private String packType;
    @ApiModelProperty(value = "装运期")
    private String trafficDate;
    @ApiModelProperty(value = "运抵国")
    private String destinationCountry;
    @ApiModelProperty(value = "外汇币制")
    private String moneyType;
    @ApiModelProperty(value = "报关商品")
    private List<ReplenishContractExcelDto> replenishmentExcel;
}
