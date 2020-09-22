package com.wisrc.crawler.webapp.service.ExternalService;

import com.wisrc.crawler.webapp.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "retail-crawler-shipment", url = "http://localhost:8080")
public interface ShipmentService {
    @RequestMapping(value = "/shipment/logistics/offersCarrier/{offerTypeCd}", method = RequestMethod.GET)
    Result getAllCarrier(@PathVariable("offerTypeCd") int offerTypeCd);

    @RequestMapping(value = "/shipment/external/removeOrder", method = RequestMethod.GET)
    Result getRemoveOrder();

}
