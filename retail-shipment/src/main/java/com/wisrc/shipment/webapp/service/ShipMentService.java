package com.wisrc.shipment.webapp.service;

import com.wisrc.shipment.webapp.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "retail-shipment-basic-04", url = "http://localhost:8080")
public interface ShipMentService {

    @RequestMapping(value = "/basic/shipment/{id}", method = RequestMethod.GET)
    Result getProduct(@PathVariable(value = "id") String id);

    @RequestMapping(value = "/basic/shipment/listid", method = RequestMethod.GET)
    Result getProducts(@RequestParam(value = "listId") String listId);

    @RequestMapping(value = "/basic/shipment/list/like", method = RequestMethod.GET)
    Result getByName(@RequestParam(value = "shipmentName") String shipmentName);

}
