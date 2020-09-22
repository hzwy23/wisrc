package com.wisrc.order.webapp.service.impl;

import com.wisrc.order.webapp.dao.OrderRemarkInfoDao;
import com.wisrc.order.webapp.entity.OrderRemarkInfoEntity;
import com.wisrc.order.webapp.service.OrderRemarkService;
import com.wisrc.order.webapp.utils.Time;
import com.wisrc.order.webapp.utils.UUIDutil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderRemarkServiceImpl implements OrderRemarkService {
    @Autowired
    private OrderRemarkInfoDao orderRemarkInfoDao;

    @Override
    public void add(OrderRemarkInfoEntity orderRemarkInfoEntity) {
        orderRemarkInfoEntity.setCreateTime(Time.getCurrentDateTime());
        orderRemarkInfoEntity.setUuid(UUIDutil.randomUUID());
        orderRemarkInfoDao.add(orderRemarkInfoEntity);
    }
}
