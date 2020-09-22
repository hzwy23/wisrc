package com.wisrc.replenishment.webapp.service;

import com.wisrc.replenishment.webapp.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient(value = "retail-replenishment-warehouse-26", url = "http://localhost:8080")
public interface WarehouseInfoService {

    /**
     * 通过仓库id获取仓库名称
     *
     * @param warehouseId
     * @return
     */
    @GetMapping(value = "/warehouse/manage/info/byid")
    Result getWarehouseName(@RequestParam("warehouseId") String warehouseId);

    /**
     * 通过数组查询仓库名称
     *
     * @param idlist
     * @return
     */
    @GetMapping(value = "/warehouse/manage/info/idlist")
    Result getWarehouseNameList(@RequestParam("warehouseId") String idlist);

}
