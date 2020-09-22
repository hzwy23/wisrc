package com.wisrc.code.webapp.vo.codeExchangeRate.add;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class AddCodeExchangeRateVO {
    @NotBlank(message = "无效的参数[asOfDate]")
    @ApiModelProperty(value = "数据日期")
    private String asOfDate;//'数据日期',

    @NotBlank(message = "无效的参数[isoCurrencyCd]")
    @ApiModelProperty(value = "当前币种")
    private String isoCurrencyCd;//'当前币种',

    @NotBlank(message = "无效的参数[targetCurrencyCd]")
    @ApiModelProperty(value = "目标币种")
    private String targetCurrencyCd;// '目标币种',

    @NotNull(message = "无效的参数[middlePrice]")

    @ApiModelProperty(value = "中间价")
    private Double middlePrice;// '中间价',
}
