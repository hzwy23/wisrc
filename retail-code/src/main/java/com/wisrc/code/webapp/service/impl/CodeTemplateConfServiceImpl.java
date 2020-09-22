package com.wisrc.code.webapp.service.impl;

import com.wisrc.code.webapp.dao.CodeTemplateConfDao;
import com.wisrc.code.webapp.entity.CodeTemplateConfEntity;
import com.wisrc.code.webapp.service.CodeTemplateConfService;
import com.wisrc.code.webapp.utils.Result;
import com.wisrc.code.webapp.utils.Toolbox;
import com.wisrc.code.webapp.vo.codeTemplateConf.CodeTemplateConfSaveVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CodeTemplateConfServiceImpl implements CodeTemplateConfService {
    @Autowired
    private CodeTemplateConfDao codeTemplateConfDao;

    @Override
    public Result getCodeTemplateConf() {
        try {
            List<CodeTemplateConfEntity> codeTemplate = codeTemplateConfDao.getCodeTemplateConf();
            return Result.success(codeTemplate);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }
    }

    @Override
    public Result saveCodeTemplateConf(CodeTemplateConfSaveVo codeTemplateConfSaveVo) {
        try {
            CodeTemplateConfEntity codeTemplateConfEntity = new CodeTemplateConfEntity();
            BeanUtils.copyProperties(codeTemplateConfSaveVo, codeTemplateConfEntity);
            codeTemplateConfEntity.setItemId(Toolbox.randomUUID());
            codeTemplateConfDao.saveCodeTemplateConf(codeTemplateConfEntity);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }

        return Result.success();
    }

    @Override
    public Result getCodeTemplateConfById(String itemId) {
        CodeTemplateConfEntity codeTemplate;

        try {
            codeTemplate = codeTemplateConfDao.getCodeTemplateConfById(itemId);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }

        return Result.success(codeTemplate);
    }
}
