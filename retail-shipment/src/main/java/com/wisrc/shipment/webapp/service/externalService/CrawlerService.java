package com.wisrc.shipment.webapp.service.externalService;

import com.wisrc.shipment.webapp.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "retail-shipment-crawler", url = "http://localhost:8080")
public interface CrawlerService {
    @RequestMapping(value = "/crawler/shipment/removeLocalOrderInfoList", method = RequestMethod.POST, consumes = "application/json")
    public Result getRemoveOrderInfoList(@RequestBody String removeOrderEnityList);

    @RequestMapping(value = "/crawler/shipment/tracingRecordList", method = RequestMethod.POST, consumes = "application/json")
    public Result geTracingRecordList(@RequestBody String tracingMapList);

    @RequestMapping(value = "/crawler/shipment/removeLocalOrderInfoBatch", method = RequestMethod.POST, consumes = "application/json")
    public Result removeLocalOrderInfoBatch(@RequestBody String parameterMapList);

}
