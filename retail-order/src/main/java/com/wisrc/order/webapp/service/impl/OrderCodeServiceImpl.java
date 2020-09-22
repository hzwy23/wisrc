package com.wisrc.order.webapp.service.impl;

import com.wisrc.order.webapp.dto.code.CodeTemplateConfEntity;
import com.wisrc.order.webapp.service.CodeOutsideService;
import com.wisrc.order.webapp.service.OrderCodeService;
import com.wisrc.order.webapp.utils.Result;
import com.wisrc.order.webapp.utils.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class OrderCodeServiceImpl implements OrderCodeService {
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
}
