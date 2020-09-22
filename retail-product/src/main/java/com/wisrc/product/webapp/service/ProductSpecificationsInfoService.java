package com.wisrc.product.webapp.service;

import com.wisrc.product.webapp.entity.ProductSpecificationsInfoEntity;

public interface ProductSpecificationsInfoService {
    void insert(ProductSpecificationsInfoEntity ele);

    void delete(String skuId);

    ProductSpecificationsInfoEntity findById(String skuId);


    ProductSpecificationsInfoEntity findBySkuId(String skuId);

    void update(ProductSpecificationsInfoEntity productSpecificationsInfoEntity, String time, String userId);

    void updateFba(ProductSpecificationsInfoEntity productSpecificationsInfoEntity, String time,String userId);
}
