package com.wisrc.replenishment.webapp.service.impl;

import com.wisrc.replenishment.webapp.dao.FbaPackUnitAttrDao;
import com.wisrc.replenishment.webapp.entity.FbaPackUnitAttrEntity;
import com.wisrc.replenishment.webapp.service.FbaPackUnitAttrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FbaPackUnitAttrServiceImpl implements FbaPackUnitAttrService {

    @Autowired
    private FbaPackUnitAttrDao fbaPackUnitAttrDao;

    @Override
    public List<FbaPackUnitAttrEntity> findAll() {
        return fbaPackUnitAttrDao.findAll();
    }
}
