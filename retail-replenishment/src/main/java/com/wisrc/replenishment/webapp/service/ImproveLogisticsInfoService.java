package com.wisrc.replenishment.webapp.service;

import com.wisrc.replenishment.webapp.entity.ImproveLogisticsInfoEntity;

public interface ImproveLogisticsInfoService {

    void ImproveLogisticsInfo(ImproveLogisticsInfoEntity ele);

    ImproveLogisticsInfoEntity get(String waybillId);

    void updateLogistics(String waybillId);
}
