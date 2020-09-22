package com.wisrc.order.webapp.service;

import com.wisrc.order.webapp.entity.OrderStatusAttr;

import java.util.List;

public interface OrderStatusService {
    List<OrderStatusAttr> getAllStatus();
}
