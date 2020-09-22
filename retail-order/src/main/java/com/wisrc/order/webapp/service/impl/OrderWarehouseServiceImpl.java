package com.wisrc.order.webapp.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wisrc.order.webapp.dto.warehouse.WarehouseManageInfoEntity;
import com.wisrc.order.webapp.service.WarehouseInfoService;
import com.wisrc.order.webapp.service.OrderWarehouseService;
import com.wisrc.order.webapp.utils.Result;
import com.wisrc.order.webapp.utils.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class OrderWarehouseServiceImpl implements OrderWarehouseService {
    @Autowired
    private WarehouseInfoService warehouseInfoService;

    @Override
    public List warehouseSelector() throws Exception {
        List result = new ArrayList();

        Result warehouseResult = warehouseInfoService.getWarehouseInfo("1", "99999", 0, 0);
        if (warehouseResult.getCode() != 200) {
            throw new Exception(warehouseResult.getMsg());
        }

        ObjectMapper mapper = new ObjectMapper();
        Map warehouseData = (Map) warehouseResult.getData();
        List<WarehouseManageInfoEntity> warehouseList = mapper.convertValue(warehouseData.get("warehouseManageInfoVOList"), new TypeReference<List<WarehouseManageInfoEntity>>() {
        });

        for (WarehouseManageInfoEntity warehouse : warehouseList) {
            result.add(ServiceUtils.idAndName(warehouse.getWarehouseId(), warehouse.getWarehouseName()));
        }

        return result;
    }
}
