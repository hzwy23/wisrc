package com.wisrc.purchase.webapp.service.externalService;

import com.wisrc.purchase.webapp.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "retail-purchase-code-07", url = "http://localhost:8080")
public interface CodeOutService {
    @RequestMapping(value = "/code/sales/purchase/plan", method = RequestMethod.GET)
    Result getPlanSales();
}
