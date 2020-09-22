package com.wisrc.rules.webapp.service;

import com.wisrc.rules.webapp.dto.code.CodeTemplateConfEntity;

import java.util.List;

public interface RulesCodeService {
    CodeTemplateConfEntity getCodeTemplateConfById() throws Exception;

    List getCountrySelector() throws Exception;

    List characteristicSelector() throws Exception;

    List getCurrencySelector() throws Exception;
}
