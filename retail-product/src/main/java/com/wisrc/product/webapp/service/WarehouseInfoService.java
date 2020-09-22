package com.wisrc.product.webapp.service;

import com.wisrc.product.webapp.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "product-retail-warehouse", url = "http://localhost:8080")
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


    @RequestMapping(value = "/warehouse/stock/cond", method = RequestMethod.POST, consumes = "application/json")
    Result getWarehouseStock(@RequestBody String voList);

}
