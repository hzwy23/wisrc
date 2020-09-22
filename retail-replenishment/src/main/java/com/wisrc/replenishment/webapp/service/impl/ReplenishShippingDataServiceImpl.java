package com.wisrc.replenishment.webapp.service.impl;

import com.wisrc.replenishment.webapp.dao.ReplenishShippingDataDao;
import com.wisrc.replenishment.webapp.dao.WaybillInfoDao;
import com.wisrc.replenishment.webapp.service.ReplenishShippingDataService;
import com.wisrc.replenishment.webapp.vo.CustomsProductListVO;
import com.wisrc.replenishment.webapp.vo.ReplenishShippingDataListVO;
import com.wisrc.replenishment.webapp.vo.ReplenishShippingDataVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ReplenishShippingDataServiceImpl implements ReplenishShippingDataService {

    @Autowired
    private ReplenishShippingDataDao replenishShippingDataDao;
    @Autowired
    private WaybillInfoDao waybillInfoDao;

    @Override
    @Transactional(transactionManager = "retailReplenishmentTransactionManager")
    public void replenishShippingData(ReplenishShippingDataListVO ele) {
        String replenishmentCommodityId = ele.getReplenishmentCommodityId();
        replenishShippingDataDao.delete(replenishmentCommodityId);
        List<ReplenishShippingDataVO> voList = ele.getList();
        for (ReplenishShippingDataVO vo : voList) {
            vo.setUuid(UUID.randomUUID().toString().replace("-", ""));
            vo.setReplenishmentCommodityId(replenishmentCommodityId);
            vo.setDeliveryNumber(vo.getNumberOfBoxes() * vo.getPackingQuantity());
            replenishShippingDataDao.insertReplenishmentMskuInfo(vo);
        }
        waybillInfoDao.updateReplenishment(replenishmentCommodityId);
    }

    @Override
    public void customsMskuInfo(CustomsProductListVO vo) {
        replenishShippingDataDao.updateCustomsMskuInfo(vo);

    }

    @Override
    public ReplenishShippingDataVO get(String uuid) {
        return replenishShippingDataDao.get(uuid);
    }
}
