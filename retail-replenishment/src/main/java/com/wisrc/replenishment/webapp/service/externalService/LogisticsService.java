package com.wisrc.replenishment.webapp.service.externalService;

import com.wisrc.replenishment.webapp.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "retail-replenishment-shipment-02", url = "http://localhost:8080")
public interface LogisticsService {
    @GetMapping(value = "/shipment/logistics/{offerId}")
    Result getLogistics(@PathVariable("offerId") String offerId);
}
