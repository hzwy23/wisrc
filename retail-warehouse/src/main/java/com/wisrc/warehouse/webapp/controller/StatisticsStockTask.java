package com.wisrc.warehouse.webapp.controller;

import com.wisrc.warehouse.webapp.entity.MskuToSkuEntity;
import com.wisrc.warehouse.webapp.entity.WsRmpWarehouseStockSumEntity;
import com.wisrc.warehouse.webapp.service.WsRmpWarehouseStockSumService;
import com.wisrc.warehouse.webapp.utils.Result;
import com.wisrc.warehouse.webapp.vo.stockVO.SkuVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Lazy(false)
@Component
@EnableScheduling
public class StatisticsStockTask {
    private final Logger logger = LoggerFactory.getLogger(StatisticsStockTask.class);

    @Autowired
    private WsRmpWarehouseStockSumService wsRmpWarehouseStockSumService;


    //每天0点存储库存汇总数据
    @Scheduled(cron = "0 2 0 * * ?")
    public void insertStockTotal() {
        Result result = new Result(200, "", null);
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String dataDt = sdf.format(System.currentTimeMillis() - 24 * 60 * 60 * 1000);
            wsRmpWarehouseStockSumService.deleteRecord(dataDt);
            List<WsRmpWarehouseStockSumEntity> list = wsRmpWarehouseStockSumService.getAllRecord();
            for (WsRmpWarehouseStockSumEntity entity : list) {
                SkuVO vo = new SkuVO();
                int totalQty = 0;
                vo.setDataDt(dataDt);
                vo.setSkuId(entity.getSkuId());
                vo.setSkuName(entity.getSkuName());
                vo.setProductQty(entity.getProductQty());
                vo.setTransportQty(entity.getLocalOnwayQty());
                vo.setPanyuStockQty(entity.getLocalStockQty());
                vo.setVirtualStockQty(entity.getVirtualStockQty());
                vo.setOverseasTransportQty(entity.getOverseasTransportQty());
                vo.setOverseasStockQty(entity.getOverseasStockQty());
                totalQty = vo.getProductQty() + vo.getTransportQty() + vo.getPanyuStockQty() + vo.getVirtualStockQty() + vo.getOverseasTransportQty() + vo.getOverseasStockQty();
                for (MskuToSkuEntity msku : entity.getMskuList()) {
                    totalQty += msku.getFbaReturnQty() + msku.getFbaTransportQty() + msku.getFbaStockQty();
                    msku.setDataDt(dataDt);
                    wsRmpWarehouseStockSumService.addMsukStock(msku);
                }
                vo.setTotalQty(totalQty);
                wsRmpWarehouseStockSumService.addStockSum(vo);
            }
        } catch (Exception e) {
            logger.warn("库存汇总存储出错！暂时无法保存", e);
        }
        if (result.getCode() != 200) {
            logger.warn("库存汇总存储出错！暂时无法保存", result);

        }
    }

    //每天0点批量存储各明细数据
    @Scheduled(cron = "0 5 0 * * ?")
    public void insertSkuStockDetail() {
        Result result = new Result(200, "", null);
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date dataDt = new Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000);
            wsRmpWarehouseStockSumService.batchInsert(dataDt);

        } catch (Exception e) {
            logger.warn("存储在仓明细数据失败", e);
        }
        if (result.getCode() != 200) {
            logger.warn("存储在仓明细数据失败", result);
        }
    }
}
