package com.wisrc.replenishment.webapp.service;

import com.wisrc.replenishment.webapp.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "retail-replenishment-product-12", url = "http://localhost:8080")
public interface ClearanProductService {

    @RequestMapping(value = "/product/productInfo/batch", method = RequestMethod.POST, consumes = "application/json")
    Result getProductInfo(@RequestBody String map);
}
