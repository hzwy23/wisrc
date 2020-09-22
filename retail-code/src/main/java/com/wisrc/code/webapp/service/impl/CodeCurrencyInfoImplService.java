package com.wisrc.code.webapp.service.impl;

import com.wisrc.code.webapp.dao.CodeCurrencyInfoDao;
import com.wisrc.code.webapp.entity.CodeCurrencyInfoEntity;
import com.wisrc.code.webapp.service.CodeCurrencyInfoService;
import com.wisrc.code.webapp.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CodeCurrencyInfoImplService implements CodeCurrencyInfoService {
    @Autowired
    private CodeCurrencyInfoDao codeCurrencyInfoDao;

    @Override
    public Result findAll() {
        List<CodeCurrencyInfoEntity> list = codeCurrencyInfoDao.findAll();
        return Result.success(list);
    }

    @Override
    public Result update(String isoCurrencyCd, String isoCurrencyEnglish, String isoCurrencyName, Integer statusCd) {
        CodeCurrencyInfoEntity entity = new CodeCurrencyInfoEntity();
        entity.setIsoCurrencyCd(isoCurrencyCd);
        entity.setIsoCurrencyEnglish(isoCurrencyEnglish);
        entity.setIsoCurrencyName(isoCurrencyName);
        entity.setStatusCd(statusCd);
        codeCurrencyInfoDao.update(entity);
        return Result.success();
    }

    @Override
    public Result delete(String isoCurrencyCd) {
        codeCurrencyInfoDao.delete(isoCurrencyCd);
        return Result.success();
    }

    @Override
    public Result insert(String isoCurrencyCd, String isoCurrencyEnglish, String isoCurrencyName) {
        CodeCurrencyInfoEntity entity = new CodeCurrencyInfoEntity();
        entity.setIsoCurrencyCd(isoCurrencyCd);
        entity.setIsoCurrencyEnglish(isoCurrencyEnglish);
        entity.setIsoCurrencyName(isoCurrencyName);
        entity.setStatusCd(1);
        try {
            codeCurrencyInfoDao.insert(entity);
            return Result.success();
        } catch (DuplicateKeyException e) {
            return new Result(9999, "该状态码已存在！无法新建", null);
        }
    }
}
