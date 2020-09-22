package com.wisrc.product.webapp.service;

import com.wisrc.product.webapp.entity.ProductDeclareLabelAttrEntity;
import com.wisrc.product.webapp.utils.Result;

public interface ProductDeclareLabelAttrService {
    Result findAll();

    Result add(ProductDeclareLabelAttrEntity entity);

    Result update(ProductDeclareLabelAttrEntity entity);

    ProductDeclareLabelAttrEntity findByLabelCd(int labelCd);

    Result getBasic();
}
