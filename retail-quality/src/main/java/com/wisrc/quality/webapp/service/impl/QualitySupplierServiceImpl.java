package com.wisrc.quality.webapp.service.impl;

import com.wisrc.quality.webapp.service.SupplierOutsideService;
import com.wisrc.quality.webapp.service.QualitySupplierService;
import com.wisrc.quality.webapp.dto.inspection.GetSupplierInfo;
import com.wisrc.quality.webapp.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class QualitySupplierServiceImpl implements QualitySupplierService {
    @Autowired
    private SupplierOutsideService supplierOutsideService;

    @Override
    public Map getSupplierNameMap(List<String> productIds) throws Exception {
        Map supplierNameMap = new HashMap();

        String supplierIds = "";
        for (int m = 0; m < productIds.size(); m++) {
            if (m == 0) {
                supplierIds += productIds.get(0);
            } else {
                supplierIds += ("," + productIds.get(m));
            }
        }

        Result supplierResult = supplierOutsideService.getSupplierBatch(supplierIds);
        if (supplierResult.getCode() != 200) {
            throw new Exception("供应商接口发生错误");
        }
        List<Map> supplierList = (List) supplierResult.getData();
        for (Map supplier : supplierList) {
            supplierNameMap.put(supplier.get("supplierId"), supplier.get("supplierName"));
        }

        return supplierNameMap;
    }

    @Override
    public List getFindKeyDealted(String findKey) throws Exception {
        List findkeyDealted = new ArrayList();

        Map supplier = supplierOutsideService.getSupplierList(findKey, 1, 99999);
        if (supplier != null) {
            findkeyDealted = getList(supplier);
        }
        if (findkeyDealted.size() > 0) {
            return findkeyDealted;
        } else {
            return null;
        }
    }

    @Override
    public Result getSupplierInfo(String supplierId) {
        GetSupplierInfo getSupplierInfo = new GetSupplierInfo();
        Map supplierResult;

        try {
            supplierResult = supplierOutsideService.getSupplierInfo(supplierId);
            System.out.println(supplierResult);
            Map supplier = (Map) supplierResult.get("supplier");
            getSupplierInfo.setContacts(String.valueOf(supplier.get("contacts")));
            Map supplierInfo = (Map) supplier.get("supplierInfo");
            getSupplierInfo.setCellphone(String.valueOf(supplierInfo.get("cellphone")));
            getSupplierInfo.setAddress(String.valueOf(supplierInfo.get("province")) + String.valueOf(supplierInfo.get("city")) + String.valueOf("county") + String.valueOf("street"));
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }

        return Result.success(getSupplierInfo);
    }

    public List getList(Map supplierResult) throws Exception {
        List result = new ArrayList();

        List<Map> supplierData = (List) supplierResult.get("suppliers");
        for (Map supplier : supplierData) {
            result.add(supplier.get("supplierId"));
        }
        System.out.println(result);

        return result;
    }
}
