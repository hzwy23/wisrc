package com.wisrc.basic.dao;

import com.wisrc.basic.entity.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CompanyBasicInfoAttrDao {
    /**
     * 出口口岸码表信息
     **/
    @Select("select delivery_port_cd,delivery_port_name from  company_delivery_port_attr")
    List<DeliveryPortAttrEntity> findDeliveryPortAttr();

    /**
     * 征免模式码表信息
     **/
    @Select("select exemption_mode_cd,exemption_mode_name from company_exempting_mode_attr")
    List<ExemptionModeAttrEntity> findExemptionModeAttr();

    /**
     * 征免性质码表信息
     **/
    @Select("select exempting_nature_cd,exempting_nature_name from company_exempting_nature_attr")
    List<ExemptingNatureAttrEntity> findExemptingNatureAttr();

    /**
     * 货币制度码表信息
     **/
    @Select("select monetary_system_cd,monetary_system_name from company_monetary_system_attr")
    List<MonetarySystemAttrEntity> findMonetarySystemAttr();

    /**
     * 包装类型码表信息
     **/
    @Select("select packing_type_cd,packing_type_name from company_packing_type_attr")
    List<PackingTypeAttrEntity> findPackingTypeAttr();

    /**
     * 贸易模式码表信息
     **/
    @Select("select trade_mode_cd,trade_mode_name from company_trade_mode_attr")
    List<TradeModeAttrEntity> findTradeModeAttr();

    /**
     * 交易模式码表
     **/
    @Select("select transaction_mode_cd,transaction_mode_name from company_transaction_mode_attr")
    List<TransactionModeAttrEntity> findTransactionModeAttr();
}
