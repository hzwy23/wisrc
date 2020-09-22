package com.wisrc.replenishment.webapp.dto.code;

import lombok.Data;

@Data
public class CodeCurrencyInfoEntity {
    private String isoCurrencyCd;
    private String isoCurrencyEnglish;
    private String isoCurrencyName;
    private Integer statusCd;
}
