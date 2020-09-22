package com.wisrc.shipment.webapp.service;

import com.wisrc.shipment.webapp.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "retail-shipment-operation-02", url = "http://localhost:8080")
public interface OperationService {

    @GetMapping("/operation/merchandise/msku/msku")
    Result getMskuById(@RequestParam("id") String id);

    @GetMapping("/operation/merchandise/msku")
    Result getMsku(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize,
                   @RequestParam("salesStatus") String salesStatus, @RequestParam("findKey") String findKey);

    @GetMapping("/operation/merchase/{mskuId}")
    Result getMskuInfo(@RequestParam("mskuId") String mskuId);

    @GetMapping("/operation/shop/{shopId}")
    Result getShopById(@RequestParam("shopId") String shopId);

}
