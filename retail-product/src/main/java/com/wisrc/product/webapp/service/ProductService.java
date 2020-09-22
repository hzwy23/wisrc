package com.wisrc.product.webapp.service;


import com.wisrc.product.webapp.filter.AuthorizationRequestInterceptor;
import com.wisrc.product.webapp.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 服务内部的接口调用
 */

@FeignClient(value = "ZUUL-SERVER", configuration = AuthorizationRequestInterceptor.class, url = "http://localhost:8080")
public interface ProductService {
    @RequestMapping(value = "/product/define/brieffuzzy", method = RequestMethod.GET)
    Result getProductInfo(@RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize);
}
