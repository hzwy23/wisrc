package com.wisrc.warehouse.webapp.service;

import com.wisrc.warehouse.webapp.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "retail-warehouse-product-11", url = "http://localhost:8080")
public interface SkuInfoService {
    @GetMapping(value = "/product/define/product/name/batch")
    Result getSkuName(@RequestParam("skuId") String[] skuIds);

    @GetMapping("/product/define/brieffuzzy")
    Result getSkuInfo(@RequestParam("skuNameZh") String skuNameZh, @RequestParam("skuId") String skuId);

    @GetMapping("/product/machine/info/{skuId}")
    Result getCompositedSku(@PathVariable("skuId") String skuId);
}

