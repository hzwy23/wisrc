package com.wisrc.rules.webapp.service;

import com.wisrc.rules.webapp.utils.Result;
import com.wisrc.rules.webapp.vo.msku.GetByMskuIdAndNameVo;
import com.wisrc.rules.webapp.vo.msku.GetMskuListByIdVo;

import java.util.List;
import java.util.Map;

public interface RulesMskuService {
    Result getSaleStatusSelector();

    Result getShopSelector();

    Result getSalesPlan(String mskuId);

    Map getMskuInfo(GetMskuListByIdVo getMskuListByIdVo) throws Exception;

    List<String> getIdByMskuIdAndName(GetByMskuIdAndNameVo getByMskuIdAndNameVo) throws Exception;
}
