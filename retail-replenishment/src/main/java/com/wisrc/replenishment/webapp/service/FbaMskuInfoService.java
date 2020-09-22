package com.wisrc.replenishment.webapp.service;

import com.wisrc.replenishment.webapp.entity.FbaMskuInfoEntity;
import com.wisrc.replenishment.webapp.entity.VReplenishmentMskuEntity;
import com.wisrc.replenishment.webapp.vo.FbaMskuInfoVO;
import com.wisrc.replenishment.webapp.vo.FbaNewMskuInfoVO;

import java.util.List;
import java.util.Map;

public interface FbaMskuInfoService {

    void saveMskuInfo(FbaMskuInfoEntity mskuInfoEntity);

    void cancelMskuInfo(FbaMskuInfoVO mskuInfoVO);

    List<FbaMskuInfoVO> findMskuByReplenId(String fbaReplenishmentId, Map mskuList, Map productMap);

    List<FbaMskuInfoVO> findMskuByReplenId(String fbaReplenishmentId);

    List<FbaMskuInfoEntity> getMskuInfo(String[] fbaIds);

    List<FbaNewMskuInfoVO> findMskuByReplenishmentId(String fbaReplenishmentId);


    /**
     * 通过商品库存编码获取当前商品FBA在途总数量
     *
     * @param mskuId
     * @return
     */
    int getFbaOnWayNum(String mskuId);

    /**
     * 查询FBA在途信息
     */
    List<VReplenishmentMskuEntity> findUnderway(String[] commodityIdList);
}
