package com.wisrc.shipment.webapp.service;

import com.wisrc.shipment.webapp.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "retail-shipment-product-03", url = "http://localhost:8080")
public interface ProductService {
    @RequestMapping(value = "/product/define/brieffuzzy", method = RequestMethod.GET)
    Result getProduct(@RequestParam("pageNum") String pageNum, @RequestParam("pageSize") String pageSize);

    @GetMapping("/product/images/{skuId}")
    Result getProductImg(@RequestParam("skuId") String skuId);

    @GetMapping("/product/declareLabelAttr/info")
    Result getProductLabelAttr(@RequestHeader("X-AUTH-ID") String userId);

    /**
     * 获取产品汇总信息
     *
     * @param map
     * @return
     */
    @RequestMapping(value = "/product/productInfo/batch", method = RequestMethod.POST, consumes = "application/json")
    Result getProductInfo(@RequestBody String map);


    @RequestMapping(value = "/product/images/list", method = RequestMethod.POST, consumes = "application/json")
    Result getProductImage(@RequestBody String skuId);

    /**
     * 获取产品特征规格
     *
     * @param skuId
     * @return
     */
    @RequestMapping(value = "/product/specifications/info/{skuId}", method = RequestMethod.GET)
    Result getProductSpecifications(@PathVariable("skuId") String skuId);

}
