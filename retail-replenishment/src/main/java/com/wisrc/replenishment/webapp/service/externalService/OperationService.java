package com.wisrc.replenishment.webapp.service.externalService;

import com.wisrc.replenishment.webapp.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "retail-replenishment-operation-03", url = "http://localhost:8080")
public interface OperationService {
    @GetMapping("/operation/shop/warehouse")
    Result getWarehouseIdByShopId(@RequestParam("shopId") String shopId);

    @RequestMapping(value = "/operation/merchandise/checkFnsku", method = RequestMethod.POST, consumes = "application/json")
    Result checkFnsku(@RequestBody String paraMapList);

    @RequestMapping(value = "/operation/merchandise/msku/batch", method = RequestMethod.GET)
    Result getMskuNameById(@RequestParam("ids") String[] ids);

    @RequestMapping(value = "/operation/merchandise/msku/fnsku/{fnsku}", method = RequestMethod.GET)
    Result getMskuInfoByFnCode(@PathVariable("fnsku") String fnsku);
}
