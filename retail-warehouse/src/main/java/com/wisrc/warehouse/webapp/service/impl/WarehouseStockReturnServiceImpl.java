package com.wisrc.warehouse.webapp.service.impl;

import com.wisrc.warehouse.webapp.vo.syncVO.StockReturnVO;
import com.wisrc.warehouse.webapp.dao.WarehouseStockReturnDao;
import com.wisrc.warehouse.webapp.service.WarehouseStockReturnService;
import com.wisrc.warehouse.webapp.utils.Result;
import com.wisrc.warehouse.webapp.vo.OutEnterWaterReturnVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WarehouseStockReturnServiceImpl implements WarehouseStockReturnService {
    @Autowired
    private WarehouseStockReturnDao warehouseStockReturnDao;

    @Override
    public void insertStock(StockReturnVO vo) {
        warehouseStockReturnDao.insertStock(vo);
    }


    @Override
    @Transactional(transactionManager = "retailWarehouseTransactionManager")
    public Result refreshLocalStock(List<StockReturnVO> stockReturnVOS) {
        warehouseStockReturnDao.deleteAllTempData();
        for (StockReturnVO stockReturnVO : stockReturnVOS) {
            warehouseStockReturnDao.insertTempStock(stockReturnVO);
            int count = warehouseStockReturnDao.findStockRecordByCondition(stockReturnVO.getbId());
            if (count > 0) {//如果该库存记录已经存在，那么就更新这条记录
                warehouseStockReturnDao.updateStockRecord(stockReturnVO);
            } else {
                warehouseStockReturnDao.insertStock(stockReturnVO);
            }
        }
        warehouseStockReturnDao.mergeData();
        return Result.success("本地更新成功");
    }

    @Override
    public void insertWater(List<OutEnterWaterReturnVO> vo) {
        for (OutEnterWaterReturnVO outEnterWaterReturnVO : vo) {
            warehouseStockReturnDao.insertWater(outEnterWaterReturnVO);
        }
    }
}
