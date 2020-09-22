package com.wisrc.basic.dao;

import com.wisrc.basic.entity.CompanyBasicInfoEntity;
import com.wisrc.basic.entity.CompanyCustomsInfoEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @user hguo
 * @date 2018-5-8
 */
@Mapper
public interface CompanyBasicInfoDao {
    /**
     * 查询公司档案基本信息
     *
     * @explain companyOnlyId 公司唯一编码  目前由于业务板块没有多数据查询功能，现在数据有且只有一条，不能带ID参数查询
     */
    @Select("select company_only_id,company_name,company_name_en,company_name_hk,company_address,company_address_en,company_address_hk,company_telephone,company_fax,taxpayer_identification_number  from  company_basic_info")
    CompanyBasicInfoEntity findBasicInfo(/*@Param("companyOnlyId") String companyOnlyId */);

    /**
     * 查询公司物流报关信息
     *
     * @explain companyOnlyId 公司唯一编码  目前由于业务板块没有多数据查询功能，现在数据有且只有一条，不能带ID参数查询
     */
    @Select("select sole_id,company_only_id,trade_mode_cd,delivery_port_cd_hy,delivery_port_cd_ky,exempting_nature_cd,exemption_mode_cd,transaction_mode_cd,monetary_system_cd,packing_type_cd,customs_code,vat_tax_number,contacts,contacts_en,cellphone,trading_country_empty,trading_country_sea," +
            "contracting_place,sourcing_destinations,warehouse_address,vat_cd,sign_mark,arrival_country   from company_customs_info")
    CompanyCustomsInfoEntity findCustomsInfo(/*@Param("companyOnlyId") String companyOnlyId  */);

    /**
     * 新增公司基本信息
     *
     * @param ele
     */
    @Insert("insert into company_basic_info(company_only_id,company_name,company_name_en,company_name_hk,company_address,company_address_en,company_address_hk,company_telephone,company_fax,taxpayer_identification_number )" +
            " values(#{companyOnlyId},#{companyName},#{companyNameEn},#{companyNameHk},#{companyAddress},#{companyAddressEn},#{companyAddressHk},#{companyTelephone},#{companyFax},#{taxpayerIdentificationNumber})")
    void addBasicInfo(CompanyBasicInfoEntity ele);

    /**
     * 新增公司物流报关信息
     *
     * @param ele
     */
    @Insert("insert into company_customs_info(sole_id,company_only_id,trade_mode_cd,delivery_port_cd_hy,delivery_port_cd_ky,exempting_nature_cd,exemption_mode_cd,transaction_mode_cd,monetary_system_cd,packing_type_cd,customs_code,vat_tax_number,contacts,contacts_en,cellphone,trading_country_empty,trading_country_sea,contracting_place,sourcing_destinations,warehouse_address,vat_cd,sign_mark,arrival_country)" +
            " values(#{soleId},#{companyOnlyId},#{tradeModeCd},#{deliveryPortCdHy},#{deliveryPortCdKy},#{exemptingNatureCd},#{exemptionModeCd},#{transactionModeCd},#{monetarySystemCd},#{packingTypeCd},#{customsCode},#{vatTaxNumber},#{contacts},#{contactsEn},#{cellphone},#{tradingCountryEmpty},#{tradingCountrySea},#{contractingPlace},#{sourcingDestinations},#{warehouseAddress},#{vatCd},#{signMark},#{arrivalCountry})")
    void addCustomsInfo(CompanyCustomsInfoEntity ele);

    /**
     * 修改公司基本信息
     *
     * @param ele
     */
    @Update("update company_basic_info set company_name = #{companyName},company_name_en = #{companyNameEn},company_name_hk = #{companyNameHk},company_address = #{companyAddress},company_address_en = #{companyAddressEn},company_address_hk = #{companyAddressHk},company_telephone = #{companyTelephone},company_fax = #{companyFax},taxpayer_identification_number = #{taxpayerIdentificationNumber} where company_only_id = #{companyOnlyId}")
    void updateBasicInfo(CompanyBasicInfoEntity ele);

    /**
     * 修改公司物流报关信息
     *
     * @param ele
     */
    @Update("update company_customs_info set trade_mode_cd = #{tradeModeCd},delivery_port_cd_hy = #{deliveryPortCdHy},delivery_port_cd_ky = #{deliveryPortCdKy},exempting_nature_cd = #{exemptingNatureCd},exemption_mode_cd = #{exemptionModeCd},transaction_mode_cd = #{transactionModeCd}," +
            "monetary_system_cd = #{monetarySystemCd},packing_type_cd = #{packingTypeCd},customs_code = #{customsCode},vat_tax_number = #{vatTaxNumber},contacts = #{contacts},contacts_en = #{contactsEn},cellphone = #{cellphone},trading_country_empty = #{tradingCountryEmpty}," +
            "trading_country_sea = #{tradingCountrySea},contracting_place = #{contractingPlace},sourcing_destinations = #{sourcingDestinations},warehouse_address = #{warehouseAddress},vat_cd = #{vatCd},sign_mark = #{signMark},arrival_country=#{arrivalCountry} where company_only_id = #{companyOnlyId}")
    void updateCustomsInfo(CompanyCustomsInfoEntity ele);

    /**
     * 查询公司档案是否有信息
     */
    @Select("select count(*) from company_basic_info")
    int findbars();
    /**
     *
     */
}
