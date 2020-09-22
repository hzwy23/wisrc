package com.wisrc.merchandise.service.externalservice;

import com.wisrc.merchandise.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "retail-merchandise-product-02", url = "http://localhost:8080")
public interface ProductService {
    /**
     * 根据产品id去获取到产品信息
     * @param skuId
     * @return
     */
    @RequestMapping("/product/productInfo/{skuId}")
    Result getSkuInfoBySkuId(@PathVariable("skuId") String skuId);
}
