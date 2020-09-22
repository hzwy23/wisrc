package com.wisrc.replenishment.webapp.service;

import com.wisrc.replenishment.webapp.utils.Result;
import com.wisrc.replenishment.webapp.dto.logisticsPlan.GetMskuPageDto;
import com.wisrc.replenishment.webapp.dto.logisticsPlan.LogisticsPlanPageDto;
import com.wisrc.replenishment.webapp.dto.msku.MskuPlanPageDTO;

import java.util.List;

public interface ReplenishmentMskuService {
    Result getSaleStatusSelector();

    Result getShopSelector();

    Result getSalesPlan(String mskuId);

    LogisticsPlanPageDto getMskuPage(GetMskuPageDto getMskuPageDto, String userId) throws Exception;

    List<MskuPlanPageDTO> getSalesPlanData(String commodityId) throws Exception;
}
