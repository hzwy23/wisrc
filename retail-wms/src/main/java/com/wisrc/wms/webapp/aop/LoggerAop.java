package com.wisrc.wms.webapp.aop;

import com.google.gson.Gson;
import com.wisrc.wms.webapp.dao.LogDao;
import com.wisrc.wms.webapp.utils.Result;
import com.wisrc.wms.webapp.utils.Time;
import com.wisrc.wms.webapp.vo.LogEntity;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Administrator
 */
@Aspect
@Component
public class LoggerAop {
    @Autowired
    private Gson gson;
    @Autowired
    private LogDao logDao;

    @AfterReturning(value = "execution(public * com.wisrc.wms.webapp.controller.WmsBillSyncController.*(..))", returning = "obj")
    public void logRecord(JoinPoint joinPoint, Result obj) {
        try {
            ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = servletRequestAttributes.getRequest();
            LogEntity logEntity = new LogEntity();
            logEntity.setRequestUrl(request.getRequestURI());
            logEntity.setRequestTime(Time.getCurrentDateTime());
            logEntity.setRequestMethod(request.getMethod());
            logEntity.setRequestParams(gson.toJson(request.getParameterMap()));
            logEntity.setRequestBodyJson(gson.toJson(joinPoint.getArgs()[0]));
            logEntity.setRequestStatus(obj.getCode());
            logEntity.setResponseData(gson.toJson(obj.getData()));
            logEntity.setResponseMessage(obj.getMsg());
            logDao.saveLog(logEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
