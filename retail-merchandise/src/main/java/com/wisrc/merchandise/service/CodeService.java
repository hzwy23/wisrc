package com.wisrc.merchandise.service;


import com.wisrc.merchandise.dto.code.CodeTemplateConfEntity;

import java.util.List;
import java.util.Map;

public interface CodeService {
    CodeTemplateConfEntity getCodeTemplateConfById() throws Exception;

    List getCountrySelector() throws Exception;

    List characteristicSelector() throws Exception;

    Map getSalesStatus() throws Exception;

    Map getKey(List keys) throws Exception;
}
