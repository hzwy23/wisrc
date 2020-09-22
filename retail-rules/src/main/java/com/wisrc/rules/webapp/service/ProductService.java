package com.wisrc.rules.webapp.service;


import com.wisrc.rules.webapp.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(value = "retail-rules-product-03", url = "http://localhost:8080")
public interface ProductService {
    @RequestMapping(value = "/product/define/brieffuzzy", method = RequestMethod.GET)
    Result getProduct(@RequestParam("pageNum") String pageNum, @RequestParam("pageSize") String pageSize);

    @GetMapping(value = "/product/productInfo/{skuId}")
    Result getProdDetails(@RequestParam("skuId") String skuId);

    // 获取所有产品分类信息
    @GetMapping(value = "/product/classify/info")
    Result getClassifyInfo() throws Exception;
}
