package com.wisrc.purchase.webapp.service.externalService;

import com.wisrc.purchase.webapp.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "retail-purchase-product-17", url = "http://localhost:8080")
public interface SkuService {
    @RequestMapping(value = "/product/define/brieffuzzy", method = RequestMethod.GET)
    Result getProductSkuInfo(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, @RequestParam("skuNameZh") String skuNameZh);

}
