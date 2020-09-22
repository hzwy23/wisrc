package com.wisrc.shipment.webapp.service;

import com.wisrc.shipment.webapp.entity.FbaWarningTypeAttrEntity;
import com.wisrc.shipment.webapp.entity.FbaWarningDaysEntity;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface ReplenishmentRemindService {

    Map<String, Object> getReplenishmentList(String shopId, Integer warningType, HashSet<String> mskuIds, String sort, Integer currentPage, Integer pageSize);

    LinkedHashMap<String, Object> getReplenishmentInfo(String replenishmentId);

    List<FbaWarningDaysEntity> getWarningDays();

    List<FbaWarningTypeAttrEntity> getWarningTypeAttr();

    List<Map<String, Object>> getProposalScheme(String uuid, String replenishmentId);

    boolean setWarningDays(String warningId, Integer days);

    boolean setSafeDays(String replenishmentId, Integer days);

}
