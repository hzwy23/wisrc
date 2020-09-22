package com.wisrc.rules.webapp.dao;

import com.wisrc.rules.webapp.entity.LogisticsClassifyRelEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface LogisticsClassifyRelDao {
    @Insert("INSERT INTO logistics_classify_relation(uuid, rule_id, classify_cd) VALUES(#{uuid}, #{ruleId}, #{classifyCd})")
    void saveSaleInvoiceLogisticsClassifyRel(LogisticsClassifyRelEntity logisticsClassifyRelEntity) throws Exception;

    @Select("SELECT classify_cd FROM logistics_classify_relation WHERE rule_id = #{ruleId}")
    List<String> getClassifyByRuleId(@Param("ruleId") String ruleId) throws Exception;

    @Delete("DELETE FROM logistics_classify_relation WHERE rule_id = #{ruleId} AND classify_cd = #{classifyCd}")
    void deleteSaleInvoiceLogisticsClassifyRel(LogisticsClassifyRelEntity logisticsClassifyRelEntity) throws Exception;
}
