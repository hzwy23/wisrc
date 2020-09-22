package com.wisrc.order.webapp.service;

import com.wisrc.order.webapp.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "retail-order-basic-08", url = "http://localhost:8080")
public interface BasicOutsideService {
    // 根据shipmentId获取信息
    @RequestMapping(value = "/basic/shipment/listid", method = RequestMethod.GET)
    Result getShipmentName(@RequestParam("listId") String listId) throws Exception;
}
