package com.wisrc.shipment.webapp.service.externalService;

import com.wisrc.shipment.webapp.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient(value = "retail-shipment-warehouse-01", url = "http://localhost:8080")
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
