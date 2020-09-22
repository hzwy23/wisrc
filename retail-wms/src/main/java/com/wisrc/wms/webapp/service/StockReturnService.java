package com.wisrc.wms.webapp.service;

import com.wisrc.wms.webapp.utils.Result;
import com.wisrc.wms.webapp.vo.ReturnVO.OutEnterWaterReturnVO;
import com.wisrc.wms.webapp.vo.ReturnVO.StockReturnVO;

import java.util.List;

public interface StockReturnService {
    void insertStock(StockReturnVO vo);

    void insertWater(List<OutEnterWaterReturnVO> vo);

    Result refreshLocalStock(List<StockReturnVO> stockReturnVOS);
}
