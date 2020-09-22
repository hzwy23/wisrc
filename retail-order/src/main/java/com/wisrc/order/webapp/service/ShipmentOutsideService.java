package com.wisrc.order.webapp.service;

import com.wisrc.order.webapp.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "retail-order-shipment-07", url = "http://localhost:8080")
public interface ShipmentOutsideService {
    // 根据物流单id获取信息
    @RequestMapping(value = "/shipment/logistics/batch", method = RequestMethod.GET)
    Result getShipment(@RequestParam("offerIds") List<String> offerIds) throws Exception;

    // 获取小包报价物流商ID
    @RequestMapping(value = "/shipment/logistics/offers/{offerTypeCd}", method = RequestMethod.GET)
    Result channelSelector(@PathVariable("offerTypeCd") Integer offerTypeCd) throws Exception;
}
