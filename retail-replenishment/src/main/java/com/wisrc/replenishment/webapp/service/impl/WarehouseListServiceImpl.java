package com.wisrc.replenishment.webapp.service.impl;

import com.wisrc.replenishment.webapp.service.WarehouseInfoService;
import com.wisrc.replenishment.webapp.service.WarehouseListService;
import com.wisrc.replenishment.webapp.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WarehouseListServiceImpl implements WarehouseListService {
    @Autowired
    WarehouseInfoService warehouseInfoService;

    @Override
    public Map getWarehouseNameList(List<String> idlist) throws Exception {
        Map warehouseMap = new HashMap();

        String warehouseIds = "";
        for (int m = 0; m < idlist.size(); m++) {
            if (m == 0) {
                warehouseIds += idlist.get(0);
            } else {
                warehouseIds += ("," + idlist.get(m));
            }
        }
        Result warehouseResult = warehouseInfoService.getWarehouseNameList(warehouseIds);
        if (warehouseResult.getCode() != 200) {
            throw new Exception("仓库接口发生错误");
        }
        List<Map> warehouseList = (List) warehouseResult.getData();
        for (Map warehouse : warehouseList) {
            warehouseMap.put(warehouse.get("warehouseId"), warehouse.get("warehouseName"));
        }
        return warehouseMap;
    }
}
