package com.wisrc.purchase.webapp.service.externalService;

import com.wisrc.purchase.webapp.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "retail-purchase-inspection-11", url = "http://localhost:8080")
public interface InspectionService {
    @RequestMapping(value = "/quality/apply/merchandise/msku", method = RequestMethod.POST, consumes = "application/json")
    Result getInspectionInfo(@RequestBody String map);

    @RequestMapping(value = "/quality/apply/merchandise/skusum", method = RequestMethod.POST, consumes = "application/json")
    Result getInspectionCompleteInfo(@RequestBody String map);

    @RequestMapping(value = "/quality/apply/by/orderId", method = RequestMethod.GET, consumes = "application/json")
    Result getInspectionApplyInfo(@RequestParam(value = "orderId", required = false) String orderId);

    @RequestMapping(value = "/quality/inspection/by/orderId", method = RequestMethod.GET, consumes = "application/json")
    Result getInspectionProductInfo(@RequestParam(value = "orderId", required = false) String orderId);
}
