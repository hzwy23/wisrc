package com.wisrc.rules.webapp.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wisrc.rules.webapp.dto.warehouse.WarehouseManageInfoEntity;
import com.wisrc.rules.webapp.service.WarehouseInfoService;
import com.wisrc.rules.webapp.service.RulesWarehouseService;
import com.wisrc.rules.webapp.utils.Result;
import com.wisrc.rules.webapp.utils.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RulesWarehouseServiceImpl implements RulesWarehouseService {
    @Autowired
    private WarehouseInfoService warehouseInfoService;

    @Override
    public List warehouseSelector() throws Exception {
        List result = new ArrayList();

        Result warehouseResult = warehouseInfoService.getWarehouseInfo(null, null, null, null);
        if (warehouseResult.getCode() != 200) {
            throw new Exception(warehouseResult.getMsg());
        }

        Map warehouseData = (Map) warehouseResult.getData();
        List<Map> warehouseList = (List) warehouseData.get("warehouseManageInfoVOList");

        for (Map warehouse : warehouseList) {
            result.add(ServiceUtils.idAndName(warehouse.get("warehouseId"), (String) warehouse.get("warehouseName")));
        }

        return result;
    }

    @Override
    public Map getWarehouseName(List warehouseIds) throws Exception {
        Map result = new HashMap();

        String warehouseId = String.join(",", warehouseIds);
        Result warehouseResult = warehouseInfoService.warehouseInfoList(warehouseId);
        if (warehouseResult.getCode() != 200) {
            throw new Exception(warehouseResult.getMsg());
        }

        ObjectMapper mapper = new ObjectMapper();
        List<WarehouseManageInfoEntity> warehouseData = mapper.convertValue(warehouseResult.getData(), new TypeReference<List<WarehouseManageInfoEntity>>() {
        });
        for (WarehouseManageInfoEntity warehouseInfo : warehouseData) {
            result.put(warehouseInfo.getWarehouseId(), warehouseInfo.getWarehouseName());
        }

        return result;
    }
}
