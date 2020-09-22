package com.wisrc.order.webapp.service.impl;

import com.wisrc.order.webapp.dao.PayTypeDao;
import com.wisrc.order.webapp.entity.PayTypeEnity;
import com.wisrc.order.webapp.service.PayTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PayTypeServiceImpl implements PayTypeService {
    @Autowired
    private PayTypeDao payTypeDao;

    @Override
    public List<PayTypeEnity> getAllPayType() {
        return payTypeDao.getAll();
    }
}
