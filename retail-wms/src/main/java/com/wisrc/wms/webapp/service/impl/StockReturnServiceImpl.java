package com.wisrc.wms.webapp.service.impl;

import com.google.gson.Gson;
import com.wisrc.wms.webapp.dao.StockReturnDao;
import com.wisrc.wms.webapp.service.StockReturnService;
import com.wisrc.wms.webapp.service.externalService.WarehouseService;
import com.wisrc.wms.webapp.utils.Result;
import com.wisrc.wms.webapp.vo.ReturnVO.OutEnterWaterReturnVO;
import com.wisrc.wms.webapp.vo.ReturnVO.StockReturnVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StockReturnServiceImpl implements StockReturnService {
    @Autowired
    private StockReturnDao stockReturnDao;
    @Autowired
    private WarehouseService warehouseService;
    @Autowired
    private Gson gson;

    @Override
    public void insertStock(StockReturnVO vo) {
        stockReturnDao.insertStock(vo);
    }

    @Override
    @Transactional(transactionManager = "retailWmsTransactionManager", rollbackFor = Exception.class)
    public void insertWater(List<OutEnterWaterReturnVO> vo) {
        warehouseService.syncStockWater(gson.toJson(vo));
        for (OutEnterWaterReturnVO outEnterWaterReturnVO : vo) {
            stockReturnDao.insertWater(outEnterWaterReturnVO);
        }
    }

    @Override
    @Transactional(transactionManager = "retailWmsTransactionManager", rollbackFor = Exception.class)
    public Result refreshLocalStock(List<StockReturnVO> stockReturnVOS) {
        warehouseService.syncWarehouseStock(gson.toJson(stockReturnVOS));
        stockReturnDao.deleteAllTempData();
        for (StockReturnVO stockReturnVO : stockReturnVOS) {
            stockReturnDao.insertTempStock(stockReturnVO);
            int count = stockReturnDao.findStockRecordByCondition(stockReturnVO.getbId());
            //如果该库存记录已经存在，那么就更新这条记录
            if (count > 0) {
                stockReturnDao.updateStockRecord(stockReturnVO);
            } else {
                stockReturnDao.insertStock(stockReturnVO);
            }
        }
        stockReturnDao.mergeData();
        return Result.success("本地更新成功");
    }
}
