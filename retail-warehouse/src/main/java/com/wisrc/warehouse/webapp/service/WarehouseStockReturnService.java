package com.wisrc.warehouse.webapp.service;

import com.wisrc.warehouse.webapp.utils.Result;
import com.wisrc.warehouse.webapp.vo.OutEnterWaterReturnVO;
import com.wisrc.warehouse.webapp.vo.syncVO.StockReturnVO;

import java.util.List;

public interface WarehouseStockReturnService {
    void insertStock(StockReturnVO vo);

    //void insertWater(OutEnterWaterReturnVO vo);

    Result refreshLocalStock(List<StockReturnVO> stockReturnVOS);

    void insertWater(List<OutEnterWaterReturnVO> vo);
}
