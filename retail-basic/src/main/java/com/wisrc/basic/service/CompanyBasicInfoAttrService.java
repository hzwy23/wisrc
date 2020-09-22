package com.wisrc.basic.service;

import com.wisrc.basic.entity.*;

import java.util.List;

public interface CompanyBasicInfoAttrService {
    /**
     * 出口口岸码表信息
     **/
    List<DeliveryPortAttrEntity> findDeliveryPortAttr();

    /**
     * 征免模式码表信息
     **/
    List<ExemptionModeAttrEntity> findExemptionModeAttr();

    /**
     * 征免性质码表信息
     **/
    List<ExemptingNatureAttrEntity> findExemptingNatureAttr();

    /**
     * 货币制度码表信息
     **/
    List<MonetarySystemAttrEntity> findMonetarySystemAttr();

    /**
     * 包装类型码表信息
     **/
    List<PackingTypeAttrEntity> findPackingTypeAttr();

    /**
     * 贸易模式码表信息
     **/
    List<TradeModeAttrEntity> findTradeModeAttr();

    /**
     * 交易模式码表
     **/
    List<TransactionModeAttrEntity> findTransactionModeAttr();
}
