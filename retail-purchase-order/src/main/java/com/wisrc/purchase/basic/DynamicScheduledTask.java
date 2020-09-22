package com.wisrc.purchase.basic;

import com.wisrc.purchase.webapp.dao.PurchaseSettingDao;
import com.wisrc.purchase.webapp.entity.GetScheduledTime;
import com.wisrc.purchase.webapp.service.PurchasePlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ScheduledFuture;

@Lazy(false)
@Component
@EnableScheduling
public class DynamicScheduledTask implements SchedulingConfigurer {
    public static String cron;
    @Autowired
    private PurchasePlanService purchasePlanService;
    @Autowired
    private PurchaseSettingDao purchaseSettingDao;
    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;
    private ScheduledFuture<?> future;

    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        return new ThreadPoolTaskScheduler();
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.addTriggerTask(new Runnable() {
            @Override
            public void run() {
                // 定时任务的业务逻辑
                purchasePlanService.savePurchasePlan();
            }
        }, new Trigger() {
            @Override
            public Date nextExecutionTime(TriggerContext triggerContext) {//设置下次定时器
                return nextExecution(triggerContext);
            }
        });
    }

    public void restart() {
        stopCron();
        future = threadPoolTaskScheduler.schedule(new Runnable() {
            @Override
            public void run() {
                // 定时任务的业务逻辑
                purchasePlanService.savePurchasePlan();
            }
        }, new Trigger() {
            @Override
            public Date nextExecutionTime(TriggerContext triggerContext) {//设置下次定时器
                return nextExecution(triggerContext);
            }
        });
    }

    public Date nextExecution(TriggerContext triggerContext) {
        try {
            GetScheduledTime scheduledTime = purchaseSettingDao.getScheduledTime();

            Calendar cal = Calendar.getInstance();
            cal.setTime(scheduledTime.getDatetime());
            int hour = cal.get(Calendar.HOUR_OF_DAY);//24小时制
            int minute = cal.get(Calendar.MINUTE);//分

            if (scheduledTime.getCalculateCycleCd() == 1) {
                cron = "0 " + minute + " " + hour + " * * ?";
            } else if (scheduledTime.getCalculateCycleCd() == 2) {
                cron = "0 " + minute + " " + hour + " ? * " + scheduledTime.getCalculateCycleWeekAttr();
            } else {
                return null;
            }

            CronTrigger trigger = new CronTrigger(cron); // 定时任务触发，可修改定时任务的执行周期

            Date nextExecDate = trigger.nextExecutionTime(triggerContext);
            return nextExecDate;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public void stopCron() {
        if (future != null) {
            future.cancel(true);//取消任务调度
        }
    }
}
