package com.wisrc.crawler.webapp.AOP;

import com.wisrc.crawler.basic.ScheduledTasks;
import com.wisrc.crawler.webapp.dao.ErrorLogDao;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ScheduledTasksAop {
    @Autowired
    private ErrorLogDao errorLogDao;

    @AfterThrowing(throwing = "ex", pointcut = "execution(public * com.wisrc.crawler.basic.ScheduledTasks.shipmentTransferInfo(..)))")
    public void doTransferInfo(Throwable ex) {
        ScheduledTasks.task1 = 0;
        errorLogDao.updateLog(1, 1);
    }

    @AfterThrowing(throwing = "ex", pointcut = "execution(public * com.wisrc.crawler.basic.ScheduledTasks.mskuStockInfo(..)))")
    public void doStockInfo(Throwable ex) {
        ScheduledTasks.task2 = 0;
        errorLogDao.updateLog(1, 2);
    }

    @AfterThrowing(throwing = "ex", pointcut = "execution(public * com.wisrc.crawler.basic.ScheduledTasks.removeOrderInfo(..)))")
    public void doOrderInfo(Throwable ex) {
        ScheduledTasks.task3 = 0;
        errorLogDao.updateLog(1, 3);
    }

    @AfterThrowing(throwing = "ex", pointcut = "execution(public * com.wisrc.crawler.basic.ScheduledTasks.shipmentInfo(..)))")
    public void doShipmentInfo(Throwable ex) {
        ScheduledTasks.task4 = 0;
        errorLogDao.updateLog(1, 4);
    }

    @AfterThrowing(throwing = "ex", pointcut = "execution(public * com.wisrc.crawler.basic.ScheduledTasks.removeOrderShipment(..)))")
    public void doOrderShipment(Throwable ex) {
        ScheduledTasks.task5 = 0;
        errorLogDao.updateLog(1, 5);
    }

    @AfterThrowing(throwing = "ex", pointcut = "execution(public * com.wisrc.crawler.basic.ScheduledTasks.mskuShelve(..)))")
    public void doShelveInfo(Throwable ex) {
        ScheduledTasks.task6 = 0;
        errorLogDao.updateLog(1, 6);
    }

    @AfterThrowing(throwing = "ex", pointcut = "execution(public * com.wisrc.crawler.basic.ScheduledTasks.mskuShelve(..)))")
    public void doShipmentTransfer(Throwable ex) {
        ScheduledTasks.task7 = 0;
        errorLogDao.updateLog(1, 7);
    }

}
