package com.wisrc.code.webapp.service.impl;

import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wisrc.code.webapp.vo.codeExchangeRate.add.AddCodeExchangeRateVO;
import com.wisrc.code.webapp.vo.codeExchangeRate.get.GetCodeExchangeRateVO;
import com.wisrc.code.webapp.vo.codeExchangeRate.set.SetCodeExchangeRateVO;
import com.wisrc.code.webapp.dao.CodeExchangeRateDao;
import com.wisrc.code.webapp.entity.CodeCurrencyInfoEntity;
import com.wisrc.code.webapp.entity.CodeExchangeRateEntity;
import com.wisrc.code.webapp.service.CodeExchangeRateService;
import com.wisrc.code.webapp.service.UserService;
import com.wisrc.code.webapp.utils.Result;
import com.wisrc.code.webapp.utils.Time;
import com.wisrc.code.webapp.utils.Toolbox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.*;

@Service
public class CodeExchangeRateImplService implements CodeExchangeRateService {
    private final Logger logger = LoggerFactory.getLogger(CodeExchangeRateImplService.class);
    @Autowired
    private CodeExchangeRateDao codeExchangeRateDao;
    @Autowired
    private EmployeeServiceImpl employeeServiceImpl;
    @Autowired
    private UserService userService;

    @Override
    public Result insert(@Valid AddCodeExchangeRateVO vo, BindingResult bindingResult, String userId) {
        //校验参数
        if (bindingResult.hasErrors()) {
            return new Result(9991, bindingResult.getFieldError().getDefaultMessage(), null);
        }
        if (vo.getIsoCurrencyCd().equals(vo.getTargetCurrencyCd())) {
            return new Result(9991, "当前币种与目标币种不能相同！", null);
        }
        CodeExchangeRateEntity entity = new CodeExchangeRateEntity();
        BeanUtils.copyProperties(vo, entity);
        entity.setUuid(Toolbox.randomUUID());
        try {
            Date date = DateUtil.parse(vo.getAsOfDate());
            entity.setAsOfDate(date);
        } catch (Exception e) {
            return new Result(9991, "数据日期[asOfDate]参数格式非法", null);
        }
        Timestamp time = Time.getCurrentTimestamp();
        entity.setCreateTime(time);
        entity.setModifyTime(time);
        entity.setCreateUser(userId);
        entity.setModifyUser(userId);
        codeExchangeRateDao.insert(entity);
        return Result.success(entity.getUuid());
    }

    @Override
    public Result update(@Valid SetCodeExchangeRateVO vo, BindingResult bindingResult, String userId) {
        //校验参数
        if (bindingResult.hasErrors()) {
            return new Result(9999, bindingResult.getFieldError().getDefaultMessage(), null);
        }
        if (vo.getIsoCurrencyCd().equals(vo.getTargetCurrencyCd())) {
            return new Result(9991, "当前币种与目标币种不能相同！", null);
        }
        CodeExchangeRateEntity entity = new CodeExchangeRateEntity();
        BeanUtils.copyProperties(vo, entity);
        try {
            Date date = DateUtil.parse(vo.getAsOfDate());
            entity.setAsOfDate(date);
        } catch (Exception e) {
            return new Result(9991, "数据日期[asOfDate]参数格式非法", null);
        }
        entity.setModifyTime(Time.getCurrentTimestamp());
        entity.setModifyUser(userId);
        codeExchangeRateDao.update(entity);
        return Result.success();
    }

    @Override
    public Result find(GetCodeExchangeRateVO vo, Integer pageNum, Integer pageSize) {
        CodeExchangeRateEntity entity = new CodeExchangeRateEntity();
        BeanUtils.copyProperties(vo, entity);
        try {
            Date date = DateUtil.parse(vo.getAsOfDate());
            entity.setAsOfDate(date);
        } catch (Exception e) {
            return new Result(9991, "数据日期[asOfDate]参数格式非法", null);
        }
        List<Map> list = new ArrayList<>();
        if (pageNum == null || pageSize == null || pageNum < 1 || pageSize < 1) {
            list = codeExchangeRateDao.find(entity);
        } else {
            PageHelper.startPage(pageNum, pageSize);
            list = codeExchangeRateDao.find(entity);
        }

        if (list == null || list.size() == 0) {
            Map reMap = new HashMap();
            PageInfo pageInfo = new PageInfo(list);
            long total = pageInfo.getTotal();
            int pages = pageInfo.getPages();
            reMap.put("total", total);
            reMap.put("pages", pages);
            reMap.put("codeExchangeRateInfoList", new ArrayList<>());
            return Result.success(reMap);
        }
        //用户
        List<String> userIdList = new ArrayList<>();
        for (Map o : list) {
            userIdList.add((String) o.get("createUser"));
            userIdList.add((String) o.get("modifyUser"));
        }
        Map userMap = new HashMap();
        try {
            userMap = userService.getUserMap(userIdList);
        } catch (Exception e) {
            logger.info("人员接口发生错误", e);
            //return new Result(9991, "人员接口发生错误", null);
        }

        //货币
        //todo  statusCd 状态 ： 正常，禁用？？？
        List<CodeCurrencyInfoEntity> currencyList = codeExchangeRateDao.getCurrencyInfo();
        Map currencyMap = new HashMap();
        for (CodeCurrencyInfoEntity o : currencyList) {
            currencyMap.put(o.getIsoCurrencyCd(), o.getIsoCurrencyName());
        }

        for (Map o : list) {
            //人员
            o.put("createUserName", userMap.get(o.get("createUser")));
            o.put("modifyUserName", userMap.get(o.get("modifyUser")));
            //中文名
            o.put("isoCurrencyName", currencyMap.get(o.get("isoCurrencyCd")));
            o.put("targetCurrencyName", currencyMap.get(o.get("targetCurrencyCd")));
            //日期
            o.put("asOfDate", DateUtil.format((Date) o.get("asOfDate"), "yyyy-MM-dd"));
            o.put("createTime", DateUtil.formatDateTime((Date) o.get("createTime")));
            o.put("modifyTime", DateUtil.formatDateTime((Date) o.get("modifyTime")));
        }

        Map reMap = new HashMap();
        PageInfo pageInfo = new PageInfo(list);
        long total = pageInfo.getTotal();
        int pages = pageInfo.getPages();
        reMap.put("total", total);
        reMap.put("pages", pages);
        reMap.put("codeExchangeRateInfoList", list);
        return Result.success(reMap);
    }

    @Override
    public Result getByUuid(String uuid) {
        Map map = codeExchangeRateDao.getByUuid(uuid);
        if (map == null) {
            return Result.success(map);
        }
        //用户
        List<String> userIdList = new ArrayList<>();
        userIdList.add((String) map.get("createUser"));
        userIdList.add((String) map.get("modifyUser"));
        Map userMap = new HashMap();
        try {
            userMap = userService.getUserMap(userIdList);
        } catch (Exception e) {
            logger.info("人员接口发生错误", e);
            //return new Result(9991, "人员接口发生错误", null);
        }

        //货币
        //todo  statusCd 状态 ： 正常，禁用？？？
        List<CodeCurrencyInfoEntity> currencyList = codeExchangeRateDao.getCurrencyInfo();
        Map currencyMap = new HashMap();
        for (CodeCurrencyInfoEntity o : currencyList) {
            currencyMap.put(o.getIsoCurrencyCd(), o.getIsoCurrencyName());
        }

        //人员
        map.put("createUserName", userMap.get(map.get("createUser")));
        map.put("modifyUserName", userMap.get(map.get("modifyUser")));
        //中文名
        map.put("isoCurrencyName", currencyMap.get(map.get("isoCurrencyCd")));
        map.put("targetCurrencyName", currencyMap.get(map.get("targetCurrencyCd")));
        //日期
        map.put("asOfDate", DateUtil.format((Date) map.get("asOfDate"), "yyyy-MM-dd"));
        map.put("createTime", DateUtil.formatDateTime((Date) map.get("createTime")));
        map.put("modifyTime", DateUtil.formatDateTime((Date) map.get("modifyTime")));
        return Result.success(map);
    }

    @Override
    public Result deleteByUuid(String uuid) {
        codeExchangeRateDao.deleteByUuid(uuid);
        return Result.success();
    }

}
