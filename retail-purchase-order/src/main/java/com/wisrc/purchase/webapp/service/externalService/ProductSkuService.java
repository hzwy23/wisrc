package com.wisrc.purchase.webapp.service.externalService;

import com.wisrc.purchase.webapp.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "retail-purchase-product-16", url = "http://localhost:8080")
public interface ProductSkuService {

    @RequestMapping(value = "/product/productInfo/batch", method = RequestMethod.POST, consumes = "application/json")
    Result getProductInfo(@RequestBody String map);

    @RequestMapping(value = "/product/define/brieffuzzy", method = RequestMethod.GET)
    Result getProductSkuInfo(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, @RequestParam("skuNameZh") String skuNameZh);
}
