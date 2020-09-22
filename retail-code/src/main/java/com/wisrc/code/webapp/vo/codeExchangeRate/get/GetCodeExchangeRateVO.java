package com.wisrc.code.webapp.vo.codeExchangeRate.get;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GetCodeExchangeRateVO {
    @ApiModelProperty(value = "数据日期")
    private String asOfDate;//'数据日期',

    @ApiModelProperty(value = "当前币种")
    private String isoCurrencyCd;//'当前币种',

    @ApiModelProperty(value = "目标币种")
    private String targetCurrencyCd;// '目标币种',

}
