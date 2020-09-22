package com.wisrc.code.webapp.service.impl;

import com.wisrc.code.webapp.dao.CodePCLabelImplDao;
import com.wisrc.code.webapp.entity.CodeProductCharacteristicLabelEntity;
import com.wisrc.code.webapp.service.CodePCLabelService;
import com.wisrc.code.webapp.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CodePCLabelImplService implements CodePCLabelService {
    @Autowired
    private CodePCLabelImplDao codePCLabelImplDao;

    @Override
    public Result findAll() {
        List<CodeProductCharacteristicLabelEntity> list = codePCLabelImplDao.findAll();
        return Result.success(list);
    }

    @Override
    public Result update(Integer productLabelCd, String productLabelDesc) {
        CodeProductCharacteristicLabelEntity entity = new CodeProductCharacteristicLabelEntity();
        entity.setProductLabelCd(productLabelCd);
        entity.setProductLabelDesc(productLabelDesc);
        codePCLabelImplDao.update(entity);
        return Result.success();
    }

    @Override
    public Result insert(Integer productLabelCd, String productLabelDesc) {
        CodeProductCharacteristicLabelEntity entity = new CodeProductCharacteristicLabelEntity();
        entity.setProductLabelCd(productLabelCd);
        entity.setProductLabelDesc(productLabelDesc);
        try {
            codePCLabelImplDao.insert(entity);
            return Result.success();
        } catch (DuplicateKeyException e) {
            return new Result(9999, "该状态码已存在！无法新建", null);
        }
    }

    @Override
    public Result delete(Integer productLabelCd) {
        codePCLabelImplDao.delete(productLabelCd);
        return Result.success();
    }
}
