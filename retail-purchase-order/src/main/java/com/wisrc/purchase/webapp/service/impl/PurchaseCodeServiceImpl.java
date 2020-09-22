package com.wisrc.purchase.webapp.service.impl;

import com.wisrc.purchase.webapp.service.PurchaseCodeService;
import com.wisrc.purchase.webapp.service.externalService.CodeOutService;
import com.wisrc.purchase.webapp.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseCodeServiceImpl implements PurchaseCodeService {
    @Autowired
    private CodeOutService codeOutService;

    @Override
    public List getPlanSales() throws Exception {
        Result codeResult = codeOutService.getPlanSales();
        if (codeResult.getCode() != 200) {
            throw new Exception(codeResult.getMsg());
        }

        return (List) codeResult.getData();
    }
}
