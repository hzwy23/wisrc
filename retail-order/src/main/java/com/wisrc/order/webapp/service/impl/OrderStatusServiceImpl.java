package com.wisrc.order.webapp.service.impl;

import com.wisrc.order.webapp.dao.OrderStatusAttrDao;
import com.wisrc.order.webapp.entity.OrderStatusAttr;
import com.wisrc.order.webapp.service.OrderStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderStatusServiceImpl implements OrderStatusService {
    @Autowired
    private OrderStatusAttrDao orderStatusAttrDao;

    @Override
    public List<OrderStatusAttr> getAllStatus() {
        return orderStatusAttrDao.findAll();
    }
}
