package com.wisrc.replenishment.webapp.service.externalService;

import com.wisrc.replenishment.webapp.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "retail-replenishment-basic-01", url = "http://localhost:8080")
public interface BasicService {
    @RequestMapping("/basic/shipment/{id}")
    Result shipmentById(@PathVariable("id") String id);

    @RequestMapping("/basic/shipment/list")
    Result getAllShipment();
}
