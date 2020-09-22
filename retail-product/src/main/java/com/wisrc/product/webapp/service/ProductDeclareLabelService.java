package com.wisrc.product.webapp.service;

import com.wisrc.product.webapp.entity.CustomLabelEntity;
import com.wisrc.product.webapp.entity.NewProductDeclareLabelEntity;
import com.wisrc.product.webapp.entity.ProductDeclareLabelAttrEntity;
import com.wisrc.product.webapp.vo.declareLabel.set.SetDeclareLabelVO;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface ProductDeclareLabelService {

    void deleteBySkuId(String skuId);


    Map<String, Object> getBySkuId(String skuId);

    List<Integer> choseLabelList(String skuId);

    List<ProductDeclareLabelAttrEntity> getAllLableAttr();


    void insertLabel(NewProductDeclareLabelEntity nPDLEntity);

    List<NewProductDeclareLabelEntity> getNPDLEList(String skuId);

    void insertCustomLabel(CustomLabelEntity customLabelEntity);

    void updateLabelList(List<SetDeclareLabelVO> declareLabelList, String time, String skuId, String userId);

    List<LinkedHashMap> getBasicsLableBySkuId(String skuId);

    List<LinkedHashMap> getCustomLableBySkuId(String skuId);

    List<LinkedHashMap> newFindBySkuId(String skuId);
}
