package com.wisrc.purchase.webapp.service.impl;

import com.wisrc.purchase.webapp.service.OutWarehouseService;
import com.wisrc.purchase.webapp.service.WarehouseService;
import com.wisrc.purchase.webapp.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WarehouseImplService implements WarehouseService {
    @Autowired
    private OutWarehouseService outWarehouseService;

    @Override
    public Map getWarehouseNameMap(List<String> warehouseIdsList) throws Exception {
        Map supplierNameMap = new HashMap();

        String warehouseIds = "";
        for (int m = 0; m < warehouseIdsList.size(); m++) {
            if (m == 0) {
                warehouseIds += warehouseIdsList.get(0);
            } else {
                warehouseIds += ("," + warehouseIdsList.get(m));
            }
        }

        Result supplierResult = outWarehouseService.getWarehouseBatch(warehouseIds);
        if (supplierResult.getCode() != 200) {
            throw new Exception("供应商接口发生错误");
        }
        List<Map> supplierList = (List) supplierResult.getData();
        for (Map supplier : supplierList) {
            supplierNameMap.put(supplier.get("warehouseId"), supplier.get("warehouseName"));
        }

        return supplierNameMap;
    }

    @Override
    public Map getTotal(String date, List skuIds) throws Exception {
        Result totalResult = outWarehouseService.getTotal(date, skuIds);
        if (totalResult.getCode() != 200) {
            throw new Exception(totalResult.getMsg());
        }

        return (Map) totalResult.getData();
    }
}
