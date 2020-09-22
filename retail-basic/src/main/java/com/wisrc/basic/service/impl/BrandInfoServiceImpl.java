package com.wisrc.basic.service.impl;

import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wisrc.basic.dao.BrandInfoDao;
import com.wisrc.basic.entity.BrandInfoEntity;
import com.wisrc.basic.service.BrandInfoService;
import com.wisrc.basic.service.BrandModifyHistoryService;
import com.wisrc.basic.service.UserService;
import com.wisrc.basic.utils.Result;
import com.wisrc.basic.utils.Time;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.sql.Timestamp;
import java.util.*;

@Service
public class BrandInfoServiceImpl implements BrandInfoService {
    private final Logger logger = LoggerFactory.getLogger(BrandInfoServiceImpl.class);

    @Autowired
    private BrandInfoDao brandInfoDao;
    @Autowired
    private BrandModifyHistoryService brandModifyHistoryService;
    @Autowired
    private UserService userService;


    @Override
    @Transactional(transactionManager = "retailBasicTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public Result insert(BrandInfoEntity entity) {
        //判断品牌名是否存在
        String brandName = entity.getBrandName();
        if (brandName == null || brandName.isEmpty()) {
            return new Result(9999, "品牌名不能为空", null);
        }

        String id = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        entity.setBrandId(id);
        entity.setCreateTime(Time.getCurrentTime());
        entity.setModifyTime(Time.getCurrentTime());
        entity.setStatusCd(1);
        //===========
        //entity.setRelProductNum();
        //===========

        try {
            brandInfoDao.insert(entity);
        } catch (DuplicateKeyException e) {
            return new Result(9999, "该品牌名" + brandName + "已存在！品牌名不区分大小写。", null);
        }

        //==============添加历史纪录
        try {
            brandModifyHistoryService.historyInsert(entity, entity.getCreateTime());
        } catch (Exception e) {
            logger.error("保存品牌历史纪录出错！", e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //回滚
            return new Result(9999, "保存品牌历史纪录出错", null);
        }

        return Result.success();
    }

    @Override
    @Transactional(transactionManager = "retailBasicTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public Result update(BrandInfoEntity entity) {

        BrandInfoEntity temp = new BrandInfoEntity();
        temp.setBrandId(entity.getBrandId());
        temp.setBrandName(entity.getBrandName().toUpperCase());

        BrandInfoEntity old = brandInfoDao.findById(entity.getBrandId());
        if (old == null) {
            return new Result(9991, "该品牌不存在", null);
        }
        String time = Time.getCurrentTime();
        entity.setModifyTime(time);
        try {
            brandInfoDao.update(entity);
        } catch (DuplicateKeyException e) {
            String brandName = entity.getBrandName();
            return new Result(9999, "该品牌名" + brandName + "已存在！品牌名不区分大小写。", null);
        }

        //==============添加历史纪录
        try {
            entity.setCreateUser(old.getCreateUser());
            brandModifyHistoryService.historyUpdate(old, entity, time);
        } catch (Exception e) {
            logger.error("保存品牌历史纪录出错！", e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //回滚
            return new Result(9999, "保存品牌历史纪录出错", null);
        }
        //==============

        return Result.success();
    }

    @Override
    public Result fuzzyFind(Integer pageNum, Integer pageSize, BrandInfoEntity entity) {
        List<Map> list;
        if (pageNum == null || pageNum < 1 || pageSize == null || pageSize < 1) {
            list = brandInfoDao.fuzzyFind(entity);
        } else {
            PageHelper.startPage(pageNum, pageSize);
            list = brandInfoDao.fuzzyFind(entity);
        }
        if (list == null || list.size() == 0) {
            Map<String, Object> map = new HashMap<>();
            map.put("total", 0);
            map.put("pages", 0);
            map.put("brandInfoList", list);
            return Result.success(map);
        }

        //账户ID与账户name匹配
        Map userMap;
        try {
            userMap = getUserMap(list);
        } catch (Exception e) {
            logger.info("账号接口发生错误", e);
            userMap = new HashMap();
        }

        for (Map o : list) {
            // //补充用户信息
            o.put("createUserName", userMap.get(o.get("createUserId")));
            o.put("modifyUserName", userMap.get(o.get("modifyUserId")));
            //时间字符串，格式：yyyy-MM-dd HH:mm:ss
            o.put("createTime", DateUtil.formatDateTime((Timestamp) o.get("createTime")));
            o.put("modifyTime", DateUtil.formatDateTime((Timestamp) o.get("modifyTime")));
        }

        Map<String, Object> map = new HashMap<>();
        PageInfo pageInfo = new PageInfo(list);
        long total = pageInfo.getTotal();
        int pages = pageInfo.getPages();
        map.put("total", total);
        map.put("pages", pages);
        map.put("brandInfoList", list);
        return Result.success(map);
    }

    private Map getUserMap(List<Map> list) throws Exception {
        List<String> userIdList = new ArrayList<>();
        for (Map o : list) {
            userIdList.add((String) o.get("createUserId"));
            userIdList.add((String) o.get("modifyUserId"));
        }
        return userService.getUserMap(userIdList);
    }

    @Override
    public Result findById(BrandInfoEntity entity) {
        Map resultMap = brandInfoDao.findByIdInMap(entity.getBrandId());
        //账户ID与账户name匹配
        Map userMap;
        try {
            List<Map> list = new ArrayList<>();
            list.add(resultMap);
            userMap = getUserMap(list);
        } catch (Exception e) {
            logger.info("账号接口发生错误", e);
            userMap = new HashMap();
        }

        //补充用户信息
        resultMap.put("createUserName", userMap.get(resultMap.get("createUserId")));
        resultMap.put("modifyUserName", userMap.get(resultMap.get("modifyUserId")));
        //时间字符串，格式：yyyy-MM-dd HH:mm:ss
        resultMap.put("createTime", DateUtil.formatDateTime((Timestamp) resultMap.get("createTime")));
        resultMap.put("modifyTime", DateUtil.formatDateTime((Timestamp) resultMap.get("modifyTime")));

        return Result.success(resultMap);
    }

    @Override
    @Transactional(transactionManager = "retailBasicTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public Result restrict(BrandInfoEntity entity) {
        BrandInfoEntity old = brandInfoDao.findById(entity.getBrandId());
        String time = Time.getCurrentTime();
        entity.setModifyTime(time);


        Integer statusCd = entity.getStatusCd();
        if (statusCd == null || statusCd < 0 || statusCd > 3) {
            return new Result(9999, "statusCd不符合要求！", null);
        } else {
            brandInfoDao.restrict(entity);

            //==============添加历史纪录
            try {
                BrandInfoEntity temp = new BrandInfoEntity();
                temp.setBrandId(old.getBrandId());
                temp.setStatusCd(old.getStatusCd());
                brandModifyHistoryService.historyUpdate(temp, entity, time);
            } catch (Exception e) {
                logger.error("保存品牌历史纪录出错！", e);
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //回滚
                return new Result(9999, "保存品牌历史纪录出错", null);
            }
            //==============
            return Result.success();
        }
    }

}
