package com.wisrc.shipment.webapp.service.externalService;

import com.wisrc.shipment.webapp.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "retail-shipment-replenishment-05", url = "http://loccalhost:8080")
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

    @RequestMapping(value = "/replenishment/fba/{fbaReplenishmentId}", method = RequestMethod.GET)
    Result getFbaInfo(@PathVariable("fbaReplenishmentId") String fbaReplenishmentId);

    @RequestMapping(value = "/replenishment/fba/fbaInfoBatch", method = RequestMethod.GET)
    Result getFbaInfoBatch(@RequestParam("fbaReplenishmentIds") String[] fbaReplenishmentId);

    @RequestMapping(value = "/replenishment/delivery/bill", method = RequestMethod.GET)
    Result getBillEstimate(@RequestParam("fbaIds") String[] fbaIds, @RequestParam("channelType") String channelType);

}
