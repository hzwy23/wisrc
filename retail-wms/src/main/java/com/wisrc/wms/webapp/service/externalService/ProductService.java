package com.wisrc.wms.webapp.service.externalService;

import com.wisrc.wms.webapp.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "retail-wms-product", url = "http://localhost:8080")
public interface ProductService {


    @RequestMapping("/product/machine/packing/material/{skuId}")
    Result getPackingInfoBySkuId(@PathVariable("skuId") String skuId);
}
