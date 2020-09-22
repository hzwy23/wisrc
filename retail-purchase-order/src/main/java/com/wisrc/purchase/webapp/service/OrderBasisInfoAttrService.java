package com.wisrc.purchase.webapp.service;

import com.wisrc.purchase.webapp.entity.CustomsTypeAtrEntity;
import com.wisrc.purchase.webapp.entity.DeliveryTypeAttrEntity;
import com.wisrc.purchase.webapp.entity.TiicketOpenAttrEntity;

import java.util.List;

public interface OrderBasisInfoAttrService {
    /**
     * 查询报关类型码表
     */
    List<CustomsTypeAtrEntity> cusomsAttr();

    /**
     * 查询交货状态码表
     */
    List<DeliveryTypeAttrEntity> deliveryAttr();

    /**
     * 查询是否开票码表
     */
    List<TiicketOpenAttrEntity> tiicketAttr();
}
