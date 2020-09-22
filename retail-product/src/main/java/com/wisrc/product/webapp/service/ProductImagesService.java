package com.wisrc.product.webapp.service;

import com.wisrc.product.webapp.entity.ProductImagesEntity;
import com.wisrc.product.webapp.utils.Result;

import java.util.List;
import java.util.Map;

public interface ProductImagesService {
    Map<String, String> insert(ProductImagesEntity productImagesEntity);

    List<ProductImagesEntity> findBySkuId(String skuId);


    void deleteBySkuId(String skuId);



    List<Map<String, String>> findBySkuISimple(String skuId);

    void updateListNewTwo(List<ProductImagesEntity> list5, String time, String skuId, String createUser);

    /**
     * 获取单所有产品的图片信息
     * @return
     */
    Result getAllSkuImgs();
}
