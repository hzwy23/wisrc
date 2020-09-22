package com.wisrc.code.webapp.service.impl;

import com.wisrc.code.webapp.dao.CodeCommonStatusAttrDao;
import com.wisrc.code.webapp.entity.CodeCommonStatusAttrEntity;
import com.wisrc.code.webapp.service.CodeCommonStatusAttrService;
import com.wisrc.code.webapp.utils.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CodeCommonStatusAttrImplService implements CodeCommonStatusAttrService {
    private final Logger logger = LoggerFactory.getLogger(CodeCommonStatusAttrImplService.class);

    @Autowired
    private CodeCommonStatusAttrDao codeCommonStatusAttrDao;

    @Override
    public Result findAll() {
        List<CodeCommonStatusAttrEntity> list = codeCommonStatusAttrDao.findAll();
        return Result.success(list);
    }

    @Override
    public Result update(Integer statusCd, String statusDesc) {
        CodeCommonStatusAttrEntity entity = new CodeCommonStatusAttrEntity();
        entity.setStatusCd(statusCd);
        entity.setStatusDesc(statusDesc);
        codeCommonStatusAttrDao.update(entity);
        return Result.success();
    }

    @Override
    public Result insert(Integer statusCd, String statusDesc) {
        CodeCommonStatusAttrEntity entity = new CodeCommonStatusAttrEntity();
        entity.setStatusCd(statusCd);
        entity.setStatusDesc(statusDesc);
        try {
            codeCommonStatusAttrDao.insert(entity);
            return Result.success();
        } catch (DuplicateKeyException e) {
            return new Result(9999, "该状态码已存在！无法新建", null);
        }
    }

    @Override
    public Result delete(Integer statusCd) {
        codeCommonStatusAttrDao.delete(statusCd);
        return Result.success();
    }
}
