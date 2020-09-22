package com.wisrc.crawler.webapp.service.ExternalService;

import com.wisrc.crawler.webapp.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "retail-crawler-warehouse", url = "http://localhost:8080")
public interface WarehouseService {
    @RequestMapping(value = "/warehouse/stock/fbaStockDetail", method = RequestMethod.PUT, consumes = "application/json")
    Result updateFbaStockDetail(@RequestBody String shipmentStockList);
}
