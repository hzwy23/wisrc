package com.wisrc.sys.webapp.service;

import com.wisrc.sys.webapp.vo.position.PositionInfoSaveVo;
import com.wisrc.sys.webapp.vo.position.PositionInfoVo;
import com.wisrc.sys.webapp.vo.position.PositionPageSelectorVo;
import com.wisrc.sys.webapp.vo.position.PositionPageVo;
import com.wisrc.sys.webapp.utils.Result;
import org.springframework.validation.BindingResult;

public interface SysPositionService {
    Result getPositionPage(PositionPageVo positionPageVo, BindingResult bindingResult);

    Result savePositionInfo(PositionInfoSaveVo positionInfoVo);

    Result editPositionInfo(PositionInfoVo positionInfoVo, String number);

    Result positionSelect(PositionPageSelectorVo positionPageSelectorVo, BindingResult bindingResult);

    Result deletePositionInfo(String positionCd);
}
