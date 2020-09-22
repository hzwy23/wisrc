package com.wisrc.order.webapp.service;

import com.wisrc.order.webapp.utils.Result;
import com.wisrc.order.webapp.vo.msku.GetByMskuIdAndNameVo;
import com.wisrc.order.webapp.vo.msku.GetMskuListByIdVo;

import java.util.List;
import java.util.Map;

public interface MskuService {
    Result getSaleStatusSelector();

    Result getShopSelector();

    Result getSalesPlan(String mskuId);

    Map getMskuInfo(GetMskuListByIdVo getMskuListByIdVo) throws Exception;

    List<String> getIdByMskuIdAndName(GetByMskuIdAndNameVo getByMskuIdAndNameVo) throws Exception;
}
