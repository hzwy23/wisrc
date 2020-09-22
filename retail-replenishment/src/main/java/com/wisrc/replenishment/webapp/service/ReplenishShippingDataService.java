package com.wisrc.replenishment.webapp.service;

import com.wisrc.replenishment.webapp.vo.CustomsProductListVO;
import com.wisrc.replenishment.webapp.vo.ReplenishShippingDataListVO;
import com.wisrc.replenishment.webapp.vo.ReplenishShippingDataVO;


public interface ReplenishShippingDataService {
    void replenishShippingData(ReplenishShippingDataListVO ele);

    void customsMskuInfo(CustomsProductListVO vo);

    ReplenishShippingDataVO get(String uuid);


}
