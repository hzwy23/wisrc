package com.wisrc.zuul.services.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.wisrc.zuul.entity.HandleLoggerEntity;
import com.wisrc.zuul.utils.PageData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


//@Service
//public class HandleLoggerServiceImpl implements HandleLoggerService {
//
//    private final Logger logger = LoggerFactory.getLogger(HandleLoggerServiceImpl.class);
//
//    @Autowired
//    private HandleLoggerDao handleLoggerDao;
//
//    @Override
//    @Async
//    public void writeHandleLog(HandleLoggerEntity hle) {
//        Map<String,String> param = hle.getParams();
//        Gson gson = new Gson();
//        hle.setParamsJson(gson.toJson(param));
//        try {
//            handleLoggerDao.save(hle);
//        } catch (Exception e) {
//            logger.error("write logger info to database failed. logger information is: {}", hle.toString());
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public LinkedHashMap findAll(int pageNum, int pageSize) {
//        PageHelper.startPage(pageNum, pageSize);
//        List<HandleLoggerEntity> list = handleLoggerDao.findALl();
//        PageInfo pageInfo = new PageInfo(list);
//        return PageData.pack(pageInfo.getTotal(), pageInfo.getPages(), "loggerList", list);
//    }
//
//    @Override
//    public LinkedHashMap search(String startTime, String endTime, String userId, String ip, String handleType, int pageNum, int pageSize) {
//        PageHelper.startPage(pageNum, pageSize);
//        List<HandleLoggerEntity> list = handleLoggerDao.search(startTime, endTime, userId, ip, handleType);
//        PageInfo pageInfo = new PageInfo(list);
//        return PageData.pack(pageInfo.getTotal(), pageInfo.getPages(), "loggerList", list);
//    }
//}
