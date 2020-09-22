package com.wisrc.rules.webapp.dao;

import com.wisrc.rules.webapp.entity.LogisticsZipCodeRelEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface LogisticsZipCodeRelDao {
    @Insert("INSERT INTO logistics_zip_code_relation(uuid, rule_id, zip_code) VALUES(#{uuid},  #{ruleId}, #{zipCode})")
    void saveSaleLogisticsZipCodeRel(LogisticsZipCodeRelEntity logisticsZipCodeRelEntity) throws Exception;

    @Select("SELECT zip_code FROM logistics_zip_code_relation WHERE rule_id = #{ruleId}")
    List<String> getZipCodeByRuleId(@Param("ruleId") String ruleId) throws Exception;

    @Delete("DELETE FROM logistics_zip_code_relation WHERE rule_id = #{ruleId} AND zip_code = #{zipCode}")
    void deleteSaleLogisticsZipCodeRel(LogisticsZipCodeRelEntity logisticsZipCodeRelEntity) throws Exception;
}
