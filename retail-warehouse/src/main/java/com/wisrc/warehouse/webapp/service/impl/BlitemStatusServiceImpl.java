package com.wisrc.warehouse.webapp.service.impl;

import com.wisrc.warehouse.webapp.dao.BlitemStatusDao;
import com.wisrc.warehouse.webapp.entity.BlitemStatusEntity;
import com.wisrc.warehouse.webapp.service.BlitemStatusService;
import com.wisrc.warehouse.webapp.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlitemStatusServiceImpl implements BlitemStatusService {
    @Autowired
    private BlitemStatusDao blitemStatusDao;

    @Override
    public Result findAll() {
        List<BlitemStatusEntity> blitemStatusEntities = blitemStatusDao.findAll();
        return Result.success(blitemStatusEntities);
    }

    @Override
    public Result findById(String statusCd) {
        BlitemStatusEntity blitemStatusEntity = blitemStatusDao.finById(statusCd);
        return Result.success(blitemStatusEntity);
    }
}
