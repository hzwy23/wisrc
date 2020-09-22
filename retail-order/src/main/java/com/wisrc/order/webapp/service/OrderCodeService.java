package com.wisrc.order.webapp.service;

import com.wisrc.order.webapp.dto.code.CodeTemplateConfEntity;

import java.util.List;

public interface OrderCodeService {
    CodeTemplateConfEntity getCodeTemplateConfById() throws Exception;

    List getCountrySelector() throws Exception;

    List characteristicSelector() throws Exception;
}
