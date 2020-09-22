package com.wisrc.replenishment.webapp.service.impl;

import com.wisrc.replenishment.webapp.dao.ImproveLogisticsInfoDao;
import com.wisrc.replenishment.webapp.dao.WaybillInfoDao;
import com.wisrc.replenishment.webapp.entity.ImproveLogisticsInfoEntity;
import com.wisrc.replenishment.webapp.service.ImproveLogisticsInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ImproveLogisticsInfoServiceImpl implements ImproveLogisticsInfoService {
    @Autowired
    private ImproveLogisticsInfoDao improveLogisticsInfoDao;
    @Autowired
    private WaybillInfoDao waybillInfoDao;

    @Override
    @Transactional(transactionManager = "retailReplenishmentTransactionManager")
    public void ImproveLogisticsInfo(ImproveLogisticsInfoEntity ele) {

        improveLogisticsInfoDao.ImproveLogisticsInfo(ele);
        waybillInfoDao.updateFreighInfoByWayBIllId(ele.getSignInDate(), ele.getWaybillId());
        waybillInfoDao.updateLogistics(ele.getWaybillId());
    }

    @Override
    public ImproveLogisticsInfoEntity get(String waybillId) {
        return improveLogisticsInfoDao.get(waybillId);
    }

    @Override
    public void updateLogistics(String waybillId) {
        waybillInfoDao.updateLogistics(waybillId);
    }
}
