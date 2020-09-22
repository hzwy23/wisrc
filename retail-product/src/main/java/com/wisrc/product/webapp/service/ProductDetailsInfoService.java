package com.wisrc.product.webapp.service;

import com.wisrc.product.webapp.entity.ProductDetailsInfoEntity;

public interface ProductDetailsInfoService {

    void insert(ProductDetailsInfoEntity ele);

    void delete(String skuId);

    ProductDetailsInfoEntity findById(String skuId);


    ProductDetailsInfoEntity findBySkuId(String skuId);

    void update(ProductDetailsInfoEntity productDetailsInfoEntity, String time, String userId);
}
