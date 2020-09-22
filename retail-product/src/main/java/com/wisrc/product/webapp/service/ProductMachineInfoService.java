package com.wisrc.product.webapp.service;

import com.wisrc.product.webapp.entity.ProductMachineInfoEntity;
import com.wisrc.product.webapp.utils.Result;

import java.util.List;
import java.util.Map;

public interface ProductMachineInfoService {
    void insert(ProductMachineInfoEntity ele);

    void delete(String skuId);

    List<ProductMachineInfoEntity> findById(String skuId);


    List<ProductMachineInfoEntity> findBySkuId(String skuId);


    void updateListNewTwo(List<ProductMachineInfoEntity> list3, String time, String skuId, String createUser);

    void updateListNewThree(List<ProductMachineInfoEntity> list3, String time, String skuId, String userId);

    List<ProductMachineInfoEntity> findPMIEntity(ProductMachineInfoEntity pMIEntity);

    Result getPackingMaterial(String skuId);

    Integer getTypeOfProdect(String dependencySkuId);

    Result checkWarehouseNum(List<Map> mapList);

    Map<String, List> getAllPackingMaterial();
}
