package com.wisrc.replenishment.webapp.service;

import com.wisrc.replenishment.webapp.entity.FbaReplenishmentLabelAttrEntity;

import java.util.List;

public interface FbaReplenishmentLabelAttrService {

    List<FbaReplenishmentLabelAttrEntity> findAll();

    FbaReplenishmentLabelAttrEntity findById(int labelCd);

    void saveLabelAttr(FbaReplenishmentLabelAttrEntity labelAttr);

    void updateLabelAttr(FbaReplenishmentLabelAttrEntity labelAttr);

    void deleteLabelAttr(int LabelCd);
}
