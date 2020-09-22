package com.wisrc.sales.webapp.timeTask;

import com.wisrc.sales.webapp.service.SynchronousSalesEstimateService;
import com.wisrc.sales.webapp.utils.Result;
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
public class SynchronousSalesEstimateTask {
    private final Logger logger = LoggerFactory.getLogger(SynchronousSalesEstimateTask.class);

    @Autowired
    private SynchronousSalesEstimateService synchronousSalesEstimateService;


    //每天4点执行
    @Scheduled(cron = "0 0 4 * * ?")
    public void getWarning() {
        Result result = new Result(200, "", null);
        try {
            result = synchronousSalesEstimateService.regularUpdate();
        } catch (Exception e) {
            logger.warn("销量预估外部接口出错！暂时无法同步", e);
            // todo 定时一分钟后重试，直到成功，期间阻塞该定时任务，防止重复
        }
        if (result.getCode() != 200) {
            logger.warn("销量预估外部接口出错！暂时无法同步", result);
            // todo 定时一分钟后重试，直到成功，期间阻塞该定时任务，防止重复

        }
    }
}
