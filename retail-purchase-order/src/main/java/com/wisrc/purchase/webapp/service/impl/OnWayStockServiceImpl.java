package com.wisrc.purchase.webapp.service.impl;

import com.wisrc.purchase.webapp.dao.OnWayStockDao;
import com.wisrc.purchase.webapp.service.OnWayStockService;
import com.wisrc.purchase.webapp.vo.stockVO.LocalOnWayProductVO;
import com.wisrc.purchase.webapp.vo.stockVO.LocalOnWayTransferVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OnWayStockServiceImpl implements OnWayStockService {
    @Autowired
    private OnWayStockDao onWayStockDao;

    @Override
    public List<LocalOnWayProductVO> get(String skuId) {
        List<LocalOnWayProductVO> voList = onWayStockDao.get(skuId);
        return voList;
    }

    @Override
    public List<LocalOnWayTransferVO> getOnWayTransfer(String skuId) {
        List<LocalOnWayTransferVO> voList = onWayStockDao.getOnWayTransfer(skuId);
        return voList;
    }
}
