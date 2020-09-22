package com.wisrc.replenishment.webapp.dto.WaybillClearance;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class AttorneyExcelDto {
    @ApiModelProperty(value = "自然年最后一天")
    private Date lastDate;
    @ApiModelProperty(value = "报关日期")
    private String customsDeclarationDate;
    @ApiModelProperty(value = "委托方")
    private String companyName;
    @ApiModelProperty(value = "主要货物名称")
    private String clearanceName;
    @ApiModelProperty(value = "HS编码")
    private String customsNumber;
    @ApiModelProperty(value = "货物总价")
    private String total;
    @ApiModelProperty(value = "贸易方式")
    private String tradeType;
    @ApiModelProperty(value = "原产地/货源地")
    private String goodsSource;
}
