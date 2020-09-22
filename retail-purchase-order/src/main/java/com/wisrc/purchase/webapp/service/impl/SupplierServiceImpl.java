package com.wisrc.purchase.webapp.service.impl;

import com.wisrc.purchase.webapp.dto.inspection.GetSupplierInfo;
import com.wisrc.purchase.webapp.service.SupplierOutsideService;
import com.wisrc.purchase.webapp.service.SupplierService;
import com.wisrc.purchase.webapp.utils.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SupplierServiceImpl implements SupplierService {

    private static final Logger logger = LoggerFactory.getLogger(SupplierServiceImpl.class);

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

        try {
            Result supplierResult = supplierOutsideService.getSupplierBatch(supplierIds);
            if (supplierResult.getCode() == 200) {
                List<Map> supplierList = (List) supplierResult.getData();
                for (Map supplier : supplierList) {
                    supplierNameMap.put(supplier.get("supplierId"), supplier.get("supplierName"));
                }
            } else {
                logger.error(supplierResult.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
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
            String success = (String) supplierResult.get("success");
            if (success == null || !"true".equalsIgnoreCase(success)) {
                return Result.failure();
            }
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

    @Override
    public List getList(Map supplierResult) throws Exception {
        List result = new ArrayList();

        List<Map> supplierData = (List) supplierResult.get("suppliers");
        for (Map supplier : supplierData) {
            result.add(supplier.get("supplierId"));
        }
        return result;
    }
}
