package com.wisrc.wms.webapp.service.externalService;

import com.wisrc.wms.webapp.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "retail-wms-replenishment", url = "http://localhost:8080")
public interface FbaReplenishmentService {
    @RequestMapping(value = "/replenishment/change/pack", method = RequestMethod.PUT, consumes = "application/json")
    Result returnPackData(String fbaPackingDataReturnVO);

    @RequestMapping(value = "/replenishment/fba/{fbaReplenishmentId}", method = RequestMethod.GET)
    Result getFbaReplenishmentInfoById(@PathVariable("fbaReplenishmentId") String fbaReplenishmentId);

    @RequestMapping(value = "/replenishment/wms/fbaDeliver", method = RequestMethod.POST, consumes = "application/json")
    Result returnFbaDeliver(@RequestBody String replenishmentOutReturnVO);
}
