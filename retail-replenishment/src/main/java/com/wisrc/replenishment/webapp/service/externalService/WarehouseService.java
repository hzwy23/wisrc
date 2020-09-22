package com.wisrc.replenishment.webapp.service.externalService;

import com.wisrc.replenishment.webapp.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "retail-replenishment-warehouse-08", url = "http://localhost:8080")
public interface WarehouseService {
    @RequestMapping(value = "/warehouse/stock/cond", method = RequestMethod.POST, consumes = "application/json")
    Result getStockBySkuIdAndWarehouseId(@RequestBody String map);

    @RequestMapping(value = "/warehouse/process/task", method = RequestMethod.POST, consumes = "application/json")
    Result addProcessBill(@RequestBody String processBill);

    @RequestMapping(value = "/warehouse/process/status/fbaReplenishment", method = RequestMethod.GET)
    Result getProcessBillByFbaId(@RequestParam("fbaReplenishmentId") String fbaReplenishmentId);


    @RequestMapping(value = "/warehouse/manage/info/byid", method = RequestMethod.GET)
    Result getWarehouseById(@RequestParam("warehouseId") String warehouseId);

}
