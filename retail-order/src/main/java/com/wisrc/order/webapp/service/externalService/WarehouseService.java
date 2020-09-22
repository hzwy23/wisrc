package com.wisrc.order.webapp.service.externalService;

import com.wisrc.order.webapp.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "retail-order-warehouse", url = "http://localhost:8080")
public interface WarehouseService {
    @RequestMapping(value = "/warehouse/stock/skuIds", method = RequestMethod.GET)
    Result getWareHouserInfo(@RequestParam(value = "skuIds") String[] skuIds);

    @RequestMapping(value = "/warehouse/stock/cond", method = RequestMethod.POST, consumes = "application/json")
    Result getWareHouserInfoBatch(@RequestBody String map);
}
