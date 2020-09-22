package com.wisrc.replenishment.webapp.service.impl;

import com.wisrc.replenishment.webapp.dao.FbaReplenishmentStatusAttrDao;
import com.wisrc.replenishment.webapp.entity.FbaReplenishmentStatusAttrEntity;
import com.wisrc.replenishment.webapp.service.FbaReplenishmentStatusAttrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("fabStatusAttrService")
public class FbaReplenishmentStatusAttrServiceImpl implements FbaReplenishmentStatusAttrService {

    @Autowired
    private FbaReplenishmentStatusAttrDao statusAttrDao;

    @Override
    public List<FbaReplenishmentStatusAttrEntity> findAll() {
        return statusAttrDao.findAll();
    }
}
