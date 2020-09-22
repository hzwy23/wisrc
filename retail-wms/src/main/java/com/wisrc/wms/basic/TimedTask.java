package com.wisrc.wms.basic;

import com.wisrc.wms.webapp.controller.WmsStockController;
import com.wisrc.wms.webapp.utils.Time;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:config/timeconfig.properties")
public class TimedTask {
    private final Logger LOGGER = LoggerFactory.getLogger(TimedTask.class);
    @Autowired
    private WmsStockController wmsStockController;
    // TODO 关闭同步库存定时任务
//    @Scheduled(cron = "${time.stock}")
//    public void refreshStockInfo() {
//        LOGGER.info("开始刷新库存数据:" + Time.getCurrentDateTime());
//        wmsStockController.autoRefreshSockData();
//        LOGGER.info("库存数据刷新完毕:" + Time.getCurrentDateTime());
//    }
//
//    @Scheduled(cron = "${time.water}")
//    public void refreshWaterInfo() {
//        LOGGER.info("开始刷新库存流水数据:" + Time.getCurrentDateTime());
//        wmsStockController.getWatercourse();
//        LOGGER.info("库存流水数据刷新完毕:" + Time.getCurrentDateTime());
//    }
}
