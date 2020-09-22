package com.wisrc.purchase.webapp.service;

import com.wisrc.purchase.webapp.utils.Result;
import com.wisrc.purchase.webapp.vo.msku.GetByMskuIdAndNameVo;
import com.wisrc.purchase.webapp.vo.msku.GetMskuListByIdVo;

import java.util.List;
import java.util.Map;

public interface PurchaseMskuService {
    Result getSaleStatusSelector();

    Result getShopSelector();

    Result getSalesPlan(String mskuId);

    Map getMskuInfo(GetMskuListByIdVo getMskuListByIdVo) throws Exception;

    List<String> getIdByMskuIdAndName(GetByMskuIdAndNameVo getByMskuIdAndNameVo) throws Exception;

    List<String> getSkuId() throws Exception;
}
