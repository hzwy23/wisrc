package com.wisrc.replenishment.webapp.service;

import com.wisrc.replenishment.webapp.dto.code.CodeTemplateConfEntity;

import java.util.List;

public interface ReplenishmentCodeService {
    CodeTemplateConfEntity getCodeTemplateConfById(String itemId) throws Exception;

    List getCountrySelector() throws Exception;

    List characteristicSelector() throws Exception;

    List getCurrencySelector() throws Exception;
}
