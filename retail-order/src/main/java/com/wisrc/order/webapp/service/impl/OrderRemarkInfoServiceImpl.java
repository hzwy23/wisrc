package com.wisrc.order.webapp.service.impl;

import com.wisrc.order.webapp.dao.OrderRemarkInfoDao;
import com.wisrc.order.webapp.entity.OrderRemarkInfoEntity;
import com.wisrc.order.webapp.service.OrderRemarkInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderRemarkInfoServiceImpl implements OrderRemarkInfoService {
    @Autowired
    private OrderRemarkInfoDao orderRemarkInfoDao;

    @Override
    public void addRemark(OrderRemarkInfoEntity entity) {
        orderRemarkInfoDao.add(entity);
    }
}
