package com.wisrc.code.webapp.vo.codeExchangeRate.show;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ShowCodeExchangeRateVO {
    private String uuid;//'唯一编码',
    private String asOfDate;//'数据日期',
    private String isoCurrencyCd;//'当前币种',
    private String isoCurrencyName;//'当前币种',
    private String targetCurrencyCd;// '目标币种',
    private String targetCurrencyName;// '目标币种',
    private Double middlePrice;// '中间价',
    private Timestamp createTime;//'创建时间',
    private String createUser;//'创建人',
    private String createUserName;//'创建人',
    private Timestamp modifyTime;// '修改时间',
    private String modifyUser;// '修改人',
    private String modifyUserName;// '修改人',
}
