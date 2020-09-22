package com.wisrc.replenishment.webapp.service.externalService;

import com.wisrc.replenishment.webapp.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "retail-replenishment-product-04", url = "http://localhost:8080")
public interface ProductService {

    @RequestMapping("/product/machine/info/{skuId}")
    Result getSkuMachineInfo(@PathVariable("skuId") String skuId);

    @RequestMapping(value = "/product/define/info/batch", method = RequestMethod.POST, consumes = "application/json")
    Result getProductInfoById(@RequestBody String skuIds);

    @RequestMapping("/product/productInfo/{skuId}")
    Result getProductInfoBySkuId(@PathVariable("skuId") String skuId);
}
