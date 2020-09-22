package com.wisrc.order.webapp.service.externalService;

import com.wisrc.order.webapp.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "retail-order-shipment", url = "http://localhost:8080")
public interface ShipmentService {
    @RequestMapping(value = "/shipment/logistics/listAllChannleName", method = RequestMethod.GET)
    Result getAllChannleName();
}
