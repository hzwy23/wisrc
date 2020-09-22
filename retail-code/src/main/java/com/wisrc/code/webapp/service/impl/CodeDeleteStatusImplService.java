package com.wisrc.code.webapp.service.impl;

import com.wisrc.code.webapp.dao.CodeDeleteStatusDao;
import com.wisrc.code.webapp.entity.CodeDeleteStatusEntity;
import com.wisrc.code.webapp.service.CodeDeleteStatusService;
import com.wisrc.code.webapp.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CodeDeleteStatusImplService implements CodeDeleteStatusService {
    @Autowired
    private CodeDeleteStatusDao codeDeleteStatusDao;

    @Override
    public Result findAll() {
        List<CodeDeleteStatusEntity> list = codeDeleteStatusDao.findAll();
        return Result.success(list);
    }

    @Override
    public Result update(Integer deleteStatus, String deleteStatusDesc) {
        CodeDeleteStatusEntity entity = new CodeDeleteStatusEntity();
        entity.setDeleteStatus(deleteStatus);
        entity.setDeleteStatusDesc(deleteStatusDesc);
        codeDeleteStatusDao.update(entity);
        return Result.success();
    }

    @Override
    public Result insert(Integer deleteStatus, String deleteStatusDesc) {
        CodeDeleteStatusEntity entity = new CodeDeleteStatusEntity();
        entity.setDeleteStatus(deleteStatus);
        entity.setDeleteStatusDesc(deleteStatusDesc);
        try {
            codeDeleteStatusDao.insert(entity);
            return Result.success();
        } catch (DuplicateKeyException e) {
            return new Result(9999, "该状态码已存在！无法新建", null);
        }
    }

    @Override
    public Result delete(Integer deleteStatus) {
        codeDeleteStatusDao.delete(deleteStatus);
        return Result.success();
    }
}
