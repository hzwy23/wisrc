package com.wisrc.shipment.webapp.service.externalService;

import com.wisrc.shipment.webapp.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "retail-shipment-operation-12", url = "http://localhost:8080")
public interface ExternalMskuService {
    // 只获取推广，在售， 清仓状态下的正常msku
    @RequestMapping(value = "/operation/merchandise/msku/fba", method = RequestMethod.GET)
    Result getMsku(@RequestParam("shopId") String shopId,
                   @RequestParam("salesStatusCd") Integer salesStatusCd,
                   @RequestParam("deliveryMode") Integer deliveryMode,
                   @RequestParam("findKey") String findKey) throws Exception;
}
