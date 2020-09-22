package com.wisrc.sys.webapp.config;


import com.wisrc.sys.webapp.utils.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionHandle {
    private final static Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);

    @ResponseBody
    @ExceptionHandler(value = GlobalException.class)
    public Result handle(Exception e) {
        logger.error("【系统异常】{}", e.getMessage());
        GlobalException ex = (GlobalException) e;
        return ex.getResult();
    }

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Result handleOther(Exception e) {
        logger.error("【系统内部异常】{}", e.getMessage());
        e.printStackTrace();
        return Result.failure(500, e.getMessage(), e.fillInStackTrace());
    }
}
