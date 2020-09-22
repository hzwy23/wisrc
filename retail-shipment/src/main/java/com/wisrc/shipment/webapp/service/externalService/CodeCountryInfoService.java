package com.wisrc.shipment.webapp.service.externalService;

import com.wisrc.shipment.webapp.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "retail-shipment-code-01", url = "http://localhost:8080")
public interface CodeCountryInfoService {

    @GetMapping("/code/codeCountryInfo")
    Result getAllCountryInfo();
}
