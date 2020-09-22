package com.wisrc.purchase.webapp.service.externalService;

import com.wisrc.purchase.webapp.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "retail-purchase-replenishment-10", url = "http://localhost:8080")
public interface FbaOutService {

    @RequestMapping(value = "replenishment/fba/OnWayNum/{mskuId}")
    Result getFbaOnWayNum(@RequestParam("mskuId") String mskuId);
}
