package com.wisrc.shipment.webapp.timeTask;

import com.wisrc.shipment.webapp.service.SmartReplenishmentService;
import com.wisrc.shipment.webapp.utils.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Lazy(false)
@Component
@EnableScheduling
public class SmartReplenishmentTask {
    private final Logger logger = LoggerFactory.getLogger(SmartReplenishmentTask.class);

    @Autowired
    private SmartReplenishmentService smartReplenishmentService;

    //每天8点，12，23 点执行
    @Scheduled(cron = "0 0 8,12,23 * * ?")
    public void getWarning() {
        Result result = new Result(200, "", null);
        try {
            result = smartReplenishmentService.addWarning();
        } catch (Exception e) {
            logger.warn("捕获预警更新失败", e);

            // todo 定时一分钟后重试，直到成功，期间阻塞该定时任务，防止重复

        }
        if (result.getCode() != 200) {
            logger.warn("捕获预警更新失败", result);

            // todo 定时一分钟后重试，直到成功，期间阻塞该定时任务，防止重复

        }
    }
}
