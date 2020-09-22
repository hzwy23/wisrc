package com.wisrc.crawler.webapp.service.ExternalService;

import com.wisrc.crawler.webapp.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "retail-crawler-replenishment", url = "http://localhost:8080")
public interface ReplenishmentService {
    @RequestMapping(value = "/replenishment/external/logisticOffer", method = RequestMethod.GET)
    Result getLogisticOffer();

    @RequestMapping(value = "/replenishment/external/updateWaybill", method = RequestMethod.POST, consumes = "application/json")
    public Result updateWaybill(@RequestBody String tracingRecordMapList);

    @RequestMapping(value = "/replenishment/external/getShipmentEnity", method = RequestMethod.GET)
    public Result getShipmentEnity();

    @RequestMapping(value = "/replenishment/external/batchUpdateSignNum", method = RequestMethod.POST, consumes = "application/json")
    public Result batchUpdateSignNum(@RequestBody String shipmentInfoEnityList);


    @RequestMapping(value = "/replenishment/external/updateStatus", method = RequestMethod.PUT)
    public Result updateStatus(@RequestBody String[] waybillIds);

    @RequestMapping(value = "/replenishment/external/shipmentInfo", method = RequestMethod.GET)
    Result getShipment();
}
