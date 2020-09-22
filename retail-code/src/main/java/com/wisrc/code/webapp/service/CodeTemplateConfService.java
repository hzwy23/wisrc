package com.wisrc.code.webapp.service;

import com.wisrc.code.webapp.vo.codeTemplateConf.CodeTemplateConfSaveVo;
import com.wisrc.code.webapp.utils.Result;

public interface CodeTemplateConfService {
    Result getCodeTemplateConf();

    Result saveCodeTemplateConf(CodeTemplateConfSaveVo codeTemplateConfSaveVo);

    Result getCodeTemplateConfById(String itemId);
}
