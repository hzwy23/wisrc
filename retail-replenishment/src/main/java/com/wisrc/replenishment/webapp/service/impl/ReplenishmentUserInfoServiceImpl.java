package com.wisrc.replenishment.webapp.service.impl;

import com.wisrc.replenishment.webapp.dao.UserInfoDao;
import com.wisrc.replenishment.webapp.entity.UserInfoEntity;
import com.wisrc.replenishment.webapp.service.ReplenishmentUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReplenishmentUserInfoServiceImpl implements ReplenishmentUserInfoService {
    @Autowired
    private UserInfoDao userInfoDao;

    @Override
    public List<UserInfoEntity> getAll() {
        return userInfoDao.getAll();
    }
}
