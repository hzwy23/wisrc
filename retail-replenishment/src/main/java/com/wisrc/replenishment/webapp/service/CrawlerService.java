package com.wisrc.replenishment.webapp.service;

import com.wisrc.replenishment.webapp.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "retail-replenishment-crawler-14", url = "http://localhost:8080")
public interface CrawlerService {
    @RequestMapping(value = "/crawler/shipment/shipmentInfo/{shipmentId}/{sellerId}", method = RequestMethod.GET)
    public Result getShipmentInfo(@PathVariable("shipmentId") String shipmentId, @PathVariable("sellerId") String sellerId);

    @RequestMapping(value = "/crawler/shipment/shipmentTransferBatch", method = RequestMethod.POST, consumes = "application/json")
    Result updateMskuSaleStock(@RequestBody String mapList);
}
