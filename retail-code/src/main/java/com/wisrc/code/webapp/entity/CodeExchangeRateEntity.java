package com.wisrc.code.webapp.entity;

import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;

@Data
public class CodeExchangeRateEntity {
    private String uuid;// 系统唯一编码'
    private Date asOfDate;//'数据日期',
    private String isoCurrencyCd;//'当前币种',
    private String targetCurrencyCd;// '目标币种',
    private Double middlePrice;// '中间价',
    private Timestamp createTime;//'创建时间',
    private String createUser;//'创建人',
    private Timestamp modifyTime;// '修改时间',
    private String modifyUser;// '修改人',
    private String deleteStatus;//'删除标示。  0：正常，1：删除',
}
