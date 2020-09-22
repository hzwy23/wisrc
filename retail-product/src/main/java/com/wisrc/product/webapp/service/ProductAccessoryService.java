package com.wisrc.product.webapp.service;

import com.wisrc.product.webapp.entity.ProductAccessoryCdAttrEntity;
import com.wisrc.product.webapp.entity.ProductAccessoryEntity;
import com.wisrc.product.webapp.vo.productAccessory.set.SetProductAccessoryVO;

import java.util.LinkedHashMap;
import java.util.List;

public interface ProductAccessoryService {
    void insert(ProductAccessoryEntity pAEntity);

    void update(List<SetProductAccessoryVO> accessoryVOList, String time, String skuId, String userId);

    List<ProductAccessoryCdAttrEntity> getAttr();

    List<ProductAccessoryEntity> findBySkuId(String skuId);

    List<LinkedHashMap> getCustomBySkuId(String skuId);

    List<LinkedHashMap> getBySkuId(String skuId);


    List<LinkedHashMap> getBasicAndCustomBySkuId(String skuId);

    List<LinkedHashMap> getAll();

    List<LinkedHashMap> getBasic();
}
