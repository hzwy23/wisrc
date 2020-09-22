package com.wisrc.order.webapp.service.impl;

import com.wisrc.order.webapp.dao.PayStatusDao;
import com.wisrc.order.webapp.entity.PayStatusEnity;
import com.wisrc.order.webapp.service.PayStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaySatusServiceImpl implements PayStatusService {
    @Autowired
    private PayStatusDao payStatusDao;

    @Override
    public List<PayStatusEnity> getAllPayStatus() {
        return payStatusDao.getPayStatus();
    }
}
