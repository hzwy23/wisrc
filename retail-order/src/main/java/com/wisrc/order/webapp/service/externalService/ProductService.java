package com.wisrc.order.webapp.service.externalService;

import com.wisrc.order.webapp.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "retail-order-product", url = "http://localhost:8080")
public interface ProductService {

    /**
     * 获取产品汇总信息
     *
     * @param map
     * @return
     */
    @RequestMapping(value = "/product/productInfo/batch", method = RequestMethod.POST, consumes = "application/json")
    Result getProductInfo(@RequestBody String map);

    /**
     * 获取产品特征规格
     *
     * @param skuuId
     * @return
     */
    @RequestMapping(value = "/product/specifications/info/{skuId}", method = RequestMethod.GET)
    Result getProductSpecifications(@PathVariable("skuId") String skuId);
}
