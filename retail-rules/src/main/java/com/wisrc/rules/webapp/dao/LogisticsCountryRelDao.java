package com.wisrc.rules.webapp.dao;

import com.wisrc.rules.webapp.entity.LogisticsCountryRelEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface LogisticsCountryRelDao {
    @Insert("INSERT INTO logistics_country_relation(uuid, rule_id, country_cd) VALUES(#{uuid}, #{ruleId}, #{countryCd})")
    void saveSaleLogisticsCountryRel(LogisticsCountryRelEntity logisticsCountryRelEntity) throws Exception;

    @Select("SELECT country_cd FROM logistics_country_relation WHERE rule_id = #{ruleId}")
    List<String> getCountryBuRuleId(@Param("ruleId") String ruleId) throws Exception;

    @Delete("DELETE FROM logistics_country_relation WHERE rule_id = #{ruleId} AND country_cd = #{countryCd}")
    void deleteSaleLogisticsCountryRel(LogisticsCountryRelEntity logisticsCountryRelEntity) throws Exception;
}
