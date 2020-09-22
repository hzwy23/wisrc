package com.wisrc.rules.webapp.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wisrc.rules.webapp.dto.code.CodeCurrencyInfoEntity;
import com.wisrc.rules.webapp.dto.code.CodeTemplateConfEntity;
import com.wisrc.rules.webapp.service.CodeOutsideService;
import com.wisrc.rules.webapp.service.RulesCodeService;
import com.wisrc.rules.webapp.utils.Result;
import com.wisrc.rules.webapp.utils.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RulesCodeServiceImpl implements RulesCodeService {
    @Autowired
    private CodeOutsideService codeOutsideService;

    @Override
    public CodeTemplateConfEntity getCodeTemplateConfById() throws Exception {
        String itemId = "3c501d24bcd84cc3a64c698483ea0e56";
        Result codeResult;

        try {
            codeResult = codeOutsideService.getCodeTemplateConfById(itemId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("code模块接口出现错误");
        }

        if (codeResult.getCode() != 200) {
            System.out.println(codeResult.getMsg());
            throw new Exception("code模块接口出现错误");
        }

        CodeTemplateConfEntity codeData = ServiceUtils.mapToBean(ServiceUtils.LinkedHashMapToMap(codeResult.getData()), new CodeTemplateConfEntity());
        return codeData;
    }

    @Override
    public List getCountrySelector() throws Exception {
        Result countryResult = codeOutsideService.getCountrySelector();
        if (countryResult.getCode() != 200) {
            throw new Exception(countryResult.getMsg());
        }

        List<Map> countryList = (List) countryResult.getData();
        return countryList;
    }

    @Override
    public List characteristicSelector() throws Exception {
        Result characteristicResult = codeOutsideService.getCharacteristicSelector();
        if (characteristicResult.getCode() != 200) {
            throw new Exception(characteristicResult.getMsg());
        }

        List<Map> characteristicList = (List) characteristicResult.getData();
        return characteristicList;
    }

    @Override
    public List getCurrencySelector() throws Exception {
        Result currencyResult = codeOutsideService.getCurrencySelector();
        if (currencyResult.getCode() != 200) {
            throw new Exception(currencyResult.getMsg());
        }

        ObjectMapper mapper = new ObjectMapper();
        List<CodeCurrencyInfoEntity> characteristicList = mapper.convertValue(currencyResult.getData(), new TypeReference<List<CodeCurrencyInfoEntity>>() {
        });
        for (int m = 0; m < characteristicList.size(); m++) {
            if (characteristicList.get(m).getStatusCd() != 1) {
                characteristicList.remove(m);
                m--;
            }
        }

        return characteristicList;
    }
}
