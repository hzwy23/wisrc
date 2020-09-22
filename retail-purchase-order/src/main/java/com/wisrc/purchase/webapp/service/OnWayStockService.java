package com.wisrc.purchase.webapp.service;

import com.wisrc.purchase.webapp.vo.stockVO.LocalOnWayProductVO;
import com.wisrc.purchase.webapp.vo.stockVO.LocalOnWayTransferVO;

import java.util.List;

public interface OnWayStockService {
    List<LocalOnWayProductVO> get(String skuId);

    List<LocalOnWayTransferVO> getOnWayTransfer(String skuId);
}
