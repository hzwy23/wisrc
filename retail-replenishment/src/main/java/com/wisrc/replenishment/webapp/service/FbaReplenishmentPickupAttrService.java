package com.wisrc.replenishment.webapp.service;

import com.wisrc.replenishment.webapp.entity.FbaReplenishmentPickupAttrEntity;

import java.util.List;

public interface FbaReplenishmentPickupAttrService {

    List<FbaReplenishmentPickupAttrEntity> findAllPickupAttr();

    FbaReplenishmentPickupAttrEntity findByPickupCd(int pickupTypeCd);
}
