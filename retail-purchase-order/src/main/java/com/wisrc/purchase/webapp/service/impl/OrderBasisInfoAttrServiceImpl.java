package com.wisrc.purchase.webapp.service.impl;

import com.wisrc.purchase.webapp.dao.OrderBasisAttrDao;
import com.wisrc.purchase.webapp.entity.CustomsTypeAtrEntity;
import com.wisrc.purchase.webapp.entity.DeliveryTypeAttrEntity;
import com.wisrc.purchase.webapp.entity.TiicketOpenAttrEntity;
import com.wisrc.purchase.webapp.service.OrderBasisInfoAttrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderBasisInfoAttrServiceImpl implements OrderBasisInfoAttrService {
    @Autowired
    private OrderBasisAttrDao orderBasisAttrDao;

    @Override
    public List<CustomsTypeAtrEntity> cusomsAttr() {
        return orderBasisAttrDao.cusomsAttr();
    }

    @Override
    public List<DeliveryTypeAttrEntity> deliveryAttr() {
        return orderBasisAttrDao.deliveryAttr();
    }

    @Override
    public List<TiicketOpenAttrEntity> tiicketAttr() {
        return orderBasisAttrDao.tiicketAttr();
    }
}
