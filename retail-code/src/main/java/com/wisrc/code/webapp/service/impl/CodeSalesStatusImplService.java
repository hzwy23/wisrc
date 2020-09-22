package com.wisrc.code.webapp.service.impl;

import com.wisrc.code.webapp.dao.CodeSalesStatusDao;
import com.wisrc.code.webapp.entity.CodeSalesStatusEntity;
import com.wisrc.code.webapp.service.CodeSalesStatusService;
import com.wisrc.code.webapp.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CodeSalesStatusImplService implements CodeSalesStatusService {
    @Autowired
    private CodeSalesStatusDao codeSalesStatusDao;

    @Override
    public Result findAll() {
        List<CodeSalesStatusEntity> list = codeSalesStatusDao.findAll();
        return Result.success(list);
    }

    @Override
    public Result update(Integer saleStatusCd, String saleStatusDesc) {
        CodeSalesStatusEntity entity = new CodeSalesStatusEntity();
        entity.setSaleStatusCd(saleStatusCd);
        entity.setSaleStatusDesc(saleStatusDesc);
        codeSalesStatusDao.update(entity);
        return Result.success();
    }

    @Override
    public Result insert(Integer saleStatusCd, String saleStatusDesc) {
        CodeSalesStatusEntity entity = new CodeSalesStatusEntity();
        entity.setSaleStatusCd(saleStatusCd);
        entity.setSaleStatusDesc(saleStatusDesc);
        try {
            codeSalesStatusDao.insert(entity);
            return Result.success();
        } catch (DuplicateKeyException e) {
            return new Result(9999, "该状态码已存在！无法新建", null);
        }
    }

    @Override
    public Result delete(Integer saleStatusCd) {
        codeSalesStatusDao.delete(saleStatusCd);
        return Result.success();
    }

    @Override
    public Result purchasePlanStatus() {
        return Result.success(codeSalesStatusDao.purchasePlanStatus());
    }
}
