package com.wisrc.code.webapp.service;

import com.wisrc.code.webapp.utils.Result;

public interface CodeCurrencyInfoService {
    Result findAll();


    Result insert(String isoCurrencyCd, String isoCurrencyEnglish, String isoCurrencyName);

    Result update(String isoCurrencyCd, String isoCurrencyEnglish, String isoCurrencyName, Integer statusCd);

    Result delete(String isoCurrencyCd);
}
