package com.wisrc.quality.webapp.service;


import com.wisrc.quality.webapp.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "retail-quality-product-09", url = "http://localhost:8080")
public interface ProductService {
    // 查询产品
    @RequestMapping(value = "/product/define/brieffuzzy", method = RequestMethod.GET)
    Result getProductInfl(@RequestParam("skuId") String skuId, @RequestParam("skuNameZh") String skuNameZh, @RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize);

    // 批量获取产品图片
    @RequestMapping(value = "/product/images/batch", method = RequestMethod.GET)
    Result getProductPicture(@RequestParam("skuId") List<String> skuId);

    // 批量获取产品中文名
    @RequestMapping(value = "/product/define/product/name/batch", method = RequestMethod.GET)
    Result getProductCN(@RequestParam("skuId") List<String> skuId);

    @RequestMapping(value = "/product/define/brieffuzzy", method = RequestMethod.GET)
    Result getProduct(@RequestParam("pageNum") String pageNum, @RequestParam("pageSize") String pageSize);
}
