package com.wisrc.product.webapp.service;

import com.wisrc.product.webapp.entity.ProductSalesInfoEntity;
import com.wisrc.product.webapp.entity.ProductSalesStatusCdAttr;
import com.wisrc.product.webapp.utils.Result;

import java.util.List;

public interface ProductSalesInfoService {
    void add(ProductSalesInfoEntity productSalesEntity);

    void update(ProductSalesInfoEntity productSalesEntity, String time, String userId);

    ProductSalesInfoEntity findBySkuId(String skuId);

    List<ProductSalesStatusCdAttr> getSalesAttr();

    Result batchFind(List<String> skuIds);

    Result batchFindPost(List<String> skuIds);
}
