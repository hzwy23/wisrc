package com.wisrc.replenishment.webapp.service;

import com.wisrc.replenishment.webapp.entity.FbaReplenishmentLabelRelEntity;

import java.util.List;

public interface FbaReplenishmentLabelRelService {

    void saveLabelRel(FbaReplenishmentLabelRelEntity labelRelEntity);

    void updateLabelStatus(String deleteStatus, String uuid);

    List<String> findlabelCdByFbaId(String fbaReplenishmentId);

    void cancelLabelStatus(int deleteStatus, String deleteRandom, String fbaReplenishmentId);

    List<FbaReplenishmentLabelRelEntity> findLabelEntity(String fbaReplenishmentId);

    void removeLabel(int deleteStatus, String deleteRandom, String uuid);

}
