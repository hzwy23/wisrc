package com.wisrc.code.webapp.dao;

import com.wisrc.code.webapp.entity.CodeCurrencyInfoEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CodeCurrencyInfoDao {
    @Select(" select  iso_currency_cd,iso_currency_english,iso_currency_name,status_cd from code_currency_info")
    List<CodeCurrencyInfoEntity> findAll();

    @Update(" update code_currency_info set iso_currency_english = #{isoCurrencyEnglish},iso_currency_name = #{isoCurrencyName},status_cd = #{statusCd} where iso_currency_cd = #{isoCurrencyCd} ")
    void update(CodeCurrencyInfoEntity entity);

    @Insert(" insert into code_currency_info (iso_currency_cd,iso_currency_english,iso_currency_name,status_cd)values(#{isoCurrencyCd},#{isoCurrencyEnglish},#{isoCurrencyName},#{statusCd})")
    void insert(CodeCurrencyInfoEntity entity);

    @Delete(" delete from code_currency_info where iso_currency_cd = #{isoCurrencyCd}  ")
    void delete(String isoCurrencyCd);
}
