package com.wisrc.replenishment.webapp.service;


import com.wisrc.replenishment.webapp.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;


@FeignClient(value = "retail-replenishment-product-20", url = "http://localhost:8080")
public interface ProductService {
    @RequestMapping(value = "/product/define/brieffuzzy", method = RequestMethod.GET)
    Result getProduct(@RequestParam("pageNum") String pageNum, @RequestParam("pageSize") String pageSize);

    @GetMapping(value = "/product/declareLabel/{storeSkuId}")
    Result getProdLabel(@RequestParam("storeSkuId") String storeSkuId);

    @GetMapping(value = "/product/productInfo/{skuId}")
    Result getProdDetails(@RequestParam("skuId") String skuId);

    @PostMapping(value = "/product/productInfo/batch", consumes = "application/json")
    Result getProdList(@RequestBody String batchSkuId);

    @GetMapping("/product/declareLabelAttr/info")
    Result getProductLabelAttr(@RequestHeader("X-AUTH-ID") String userId);

    @PostMapping(value = "/product/specifications/update", consumes = "application/json")
    Result syncProductSpecifications(@RequestBody String specificationData, @RequestHeader("X-AUTH-ID") String userId);

    @RequestMapping(value = "/product/define/info/batch", method = RequestMethod.POST, consumes = "application/json")
    Result getProductInfoById(@RequestBody String skuIds);
}
