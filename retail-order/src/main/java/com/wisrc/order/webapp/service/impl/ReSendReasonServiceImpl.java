package com.wisrc.order.webapp.service.impl;

import com.wisrc.order.webapp.dao.ReSendReasonDao;
import com.wisrc.order.webapp.entity.ReSendReasonEnity;
import com.wisrc.order.webapp.service.ReSendReasonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(transactionManager = "retailOrderTransactionManager")
public class ReSendReasonServiceImpl implements ReSendReasonService {
    @Autowired
    private ReSendReasonDao reSendReasonDao;

    @Override
    public void addReason(ReSendReasonEnity reasonEnity) {
        reSendReasonDao.add(reasonEnity);
    }

    @Override
    public List<ReSendReasonEnity> getAllReason() {
        return reSendReasonDao.getAll();
    }

    @Override
    public void deleteReason(String reasonId) {
        reSendReasonDao.deleteById(reasonId);
    }
}
