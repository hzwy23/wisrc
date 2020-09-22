package com.wisrc.order.webapp.service;

import com.wisrc.order.webapp.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "retail-order-warehouse-06", url = "http://localhost:8080")
public interface WarehouseInfoService {

    /**
     * 通过仓库id获取仓库名称
     *
     * @param warehouseId
     * @return
     */
    @GetMapping(value = "/warehouse/manage/info/byid")
    Result getWarehouseName(@RequestParam("warehouseId") String warehouseId);

    @GetMapping(value = "/warehouse/manage/info")
    Result getWarehouseInfo(@RequestParam("pageNum") String pageNum, @RequestParam("pageSize") String pageSize, @RequestParam("statusCd") Integer statusCd, @RequestParam("typeCd") Integer typeCd);
}
