package com.wisrc.replenishment.webapp.service;

import com.wisrc.replenishment.webapp.entity.*;

import java.util.List;

public interface WaybillInfoAttrService {
    /**
     * 报关类型码表
     *
     * @return
     */
    List<CustomsAttrEntity> findCustomsAttr();

    /**
     * 运单异常状态码表
     *
     * @return
     */
    List<ExceptionTypeAttrEntity> findExceptionAttr();

    /**
     * 物流状态码表
     *
     * @return
     */
    List<LogisticsTypeAttrEntity> findLogisticsAttr();

    /**
     * 重量类型码表
     *
     * @return
     */
    List<WeightTypeAttrEntity> findWeightAttr();


    /**
     * 单位码表
     *
     * @return
     */
    List<DeclareMskuUnitAttrEntity> findUnitAttr(Integer unitCd);
}
