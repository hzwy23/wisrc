package com.wisrc.product.webapp.service;


import com.wisrc.product.webapp.entity.ProductPackingInfoEntity;

public interface ProductPackingInfoService {
    void insert(ProductPackingInfoEntity pPIEntity);

    void update(ProductPackingInfoEntity pPIEntity, String time, String userId);

    ProductPackingInfoEntity findBySkuId(String skuId);
}
