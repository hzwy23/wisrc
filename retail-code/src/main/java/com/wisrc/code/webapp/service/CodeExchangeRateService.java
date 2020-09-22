package com.wisrc.code.webapp.service;

import com.wisrc.code.webapp.vo.codeExchangeRate.add.AddCodeExchangeRateVO;
import com.wisrc.code.webapp.vo.codeExchangeRate.get.GetCodeExchangeRateVO;
import com.wisrc.code.webapp.vo.codeExchangeRate.set.SetCodeExchangeRateVO;
import com.wisrc.code.webapp.utils.Result;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;

public interface CodeExchangeRateService {
    Result insert(@Valid AddCodeExchangeRateVO vo, BindingResult bindingResult, String userId);

    Result update(@Valid SetCodeExchangeRateVO vo, BindingResult bindingResult, String userId);

    Result find(GetCodeExchangeRateVO vo, Integer pageNum, Integer pageSize);

    Result getByUuid(String uuid);

    Result deleteByUuid(String uuid);

}
