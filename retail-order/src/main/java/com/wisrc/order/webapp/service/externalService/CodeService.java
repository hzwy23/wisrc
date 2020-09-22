package com.wisrc.order.webapp.service.externalService;

import com.wisrc.order.webapp.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "retail-order-code", url = "http://localhost:8080")
public interface CodeService {
    @RequestMapping(value = "/code/codeCountryInfo", method = RequestMethod.GET)
    Result getCountryName();
}
