package com.wisrc.zuul.controller;

import com.wisrc.zuul.utils.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;

//@RestController
//public class HandleLoggerController {
//
//    private final Logger logger = LoggerFactory.getLogger(HandleLoggerController.class);
//    @Autowired
//    private HandleLoggerService handleLoggerService;
//
//    @RequestMapping(value = "/logger", method = RequestMethod.GET)
//    public Result getLoggers(@RequestParam(value = "pageNum", defaultValue = "0", required = true) int pageNum,
//                             @RequestParam(value = "pageSize", defaultValue = "0", required = true) int pageSize) {
//        logger.debug("分页查询日志信息，页码是{}, 每页条数是：{}", pageNum, pageSize);
//        if (pageNum <= 0 || pageSize <= 0) {
//            return Result.failure(500, "参数错误", "请输入正确的分页参数pageNum和pageSize必须是大于0的整数");
//        }
//        LinkedHashMap result = handleLoggerService.findAll(pageNum, pageSize);
//        return Result.success(result);
//    }
//
//    @RequestMapping(value = "/logger/search", method = RequestMethod.GET)
//    public Result search(@RequestParam(value = "startTime", required = false) String startTime,
//                         @RequestParam(value = "endTime", required = false) String endTime,
//                         @RequestParam(value = "userId", required = false) String userId,
//                         @RequestParam(value = "ip", required = false) String ip,
//                         @RequestParam(value = "handleType", required = false) String handleType,
//                         @RequestParam(value = "pageNum", required = true) int pageNum,
//                         @RequestParam(value = "pageSize", required = true) int pageSize) {
//        if (pageNum <= 0 || pageSize <= 0) {
//            return Result.failure(500, "参数错误", "请输入正确的分页参数pageNum和pageSize必须是大于0的整数");
//        }
//        LinkedHashMap result = handleLoggerService.search(startTime, endTime, userId, ip, handleType, pageNum, pageSize);
//        return Result.success(result);
//    }
//}
