package com.wisrc.product.webapp.service;

import com.wisrc.product.webapp.utils.Result;
import com.wisrc.product.webapp.vo.wms.ProductTypeVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(value = "product-retail-wms", url = "http://localhost:8080")

public interface WmsSyncService {
    @RequestMapping(value = "/wms/add/product/type", method = RequestMethod.POST, consumes = "application/json")
    Result addProductType(@RequestBody List<ProductTypeVO> entityList);

    @RequestMapping(value = "/wms/add/product", method = RequestMethod.POST, consumes = "application/json")
    Result addProduct(@RequestBody String entityList);
}
