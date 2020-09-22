package com.wisrc.warehouse.webapp.service.externalService;

import com.wisrc.warehouse.webapp.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient(value = "retail-warehouse-product-04", url = "http://localhost:8080")
public interface ExternalProductService {
    @RequestMapping(value = "/product/define/brieffuzzy", method = RequestMethod.GET)
    Result getProductSkuInfo(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, @RequestParam("skuNameZh") String skuNameZh);

    //查询单个产品的基础信息
    @RequestMapping(value = "/product/productInfo/batch", method = RequestMethod.POST, consumes = "application/json")
    Result getProductInfo(@RequestBody Map batchSkuId);


    @RequestMapping(value = "/product/define/sku", method = RequestMethod.GET)
    Result getAllSku();

    @RequestMapping("/product/define/{skuId}")
    Result getSkuById(@PathVariable("skuId") String skuId);
}
