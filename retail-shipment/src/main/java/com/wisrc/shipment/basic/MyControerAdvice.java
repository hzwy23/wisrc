package com.wisrc.shipment.basic;

import com.wisrc.shipment.webapp.entity.exception.MyException;
import com.wisrc.shipment.webapp.utils.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class MyControerAdvice {
    @ExceptionHandler(value = MyException.class)
    @ResponseBody
    public Result defaultErrorHandler(HttpServletRequest req, MyException e) {
        if (e.getErrorCode() == 391) {
            return Result.failure(391, "已经删除，无法修改", null);
        }
        return Result.failure(390, "物流商名称，目的地，渠道名重复", null);
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result defaultErrorHandler(HttpServletRequest req, Exception e) {
        return Result.failure(390, "服务异常", e.getMessage());
    }

}
