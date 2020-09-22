package com.wisrc.rules.webapp.service.impl;

import com.wisrc.rules.webapp.service.ProdService;
import com.wisrc.rules.webapp.service.ProductService;
import com.wisrc.rules.webapp.utils.Result;
import com.wisrc.rules.webapp.utils.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ProdServiceImpl implements ProdService {
    @Autowired
    private ProductService productService;

    public List classifySelector() throws Exception {
        List<Map> result = new ArrayList();

        Result classifyResult = productService.getClassifyInfo();
        if (classifyResult.getCode() != 200) {
            throw new Exception(classifyResult.getMsg());
        }

        List<Map> classifyData = (List) classifyResult.getData();
        for (Map classify : classifyData) {
            result.add(ServiceUtils.idAndName(classify.get("classifyCd"), (String) classify.get("classifyNameCh")));
        }

        return result;
    }
}
