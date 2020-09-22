package com.wisrc.product.webapp.service;

import com.wisrc.product.webapp.entity.ProductDeclareInfoEntity;

public interface ProductDeclareInfoService {
    void insert(ProductDeclareInfoEntity ele);

    void delete(String skuId);

    ProductDeclareInfoEntity findById(String skuId);


    ProductDeclareInfoEntity findBySkuId(String skuId);

    void update(ProductDeclareInfoEntity productDeclareInfoEntity, String time,String userId);
}
